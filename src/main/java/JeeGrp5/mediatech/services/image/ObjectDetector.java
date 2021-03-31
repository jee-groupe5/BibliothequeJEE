package JeeGrp5.mediatech.services.image;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Batchifier;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.util.JsonUtils;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectDetector {
    private static final Logger log = LoggerFactory.getLogger(ObjectDetector.class);

    public ObjectDetector() {
    }

    public Classifications detectObjects(String path) throws IOException, ModelException, TranslateException {
        if (!"TensorFlow".equals(Engine.getInstance().getEngineName())) {
            return null;
        }

        Path imageFile = Paths.get("D:\\Wawa\\Téléchargements\\photo-1526265218618-bdbe4fdb5360.jfif");
        Image img = ImageFactory.getInstance().fromFile(imageFile);

        String modelUrl =
                "http://download.tensorflow.org/models/object_detection/tf2/20200711/ssd_mobilenet_v2_320x320_coco17_tpu-8.tar.gz";

        Criteria<Image, Classifications> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class, Classifications.class)
                        .optModelUrls(modelUrl)
                        // saved_model.pb file is in the subfolder of the model archive file
                        .optModelName("ssd_mobilenet_v2_320x320_coco17_tpu-8/saved_model")
                        .optTranslator(new MyTranslator())
                        .optProgress(new ProgressBar())
                        .build();

        try (ZooModel<Image, Classifications> model = ModelZoo.loadModel(criteria);
             Predictor<Image, Classifications> predictor = model.newPredictor()) {
            return predictor.predict(img);
        }
    }

    private static final class Item {
        int id;

        @SerializedName("display_name")
        String displayName;
    }

    private static final class MyTranslator implements Translator<Image, Classifications> {
        private Map<Integer, String> classes;

        @Value("${mediatech.ai.threshold}")
        private float threshold;

        public MyTranslator() {

        }

        @Override
        public NDList processInput(TranslatorContext ctx, Image input) {
            // input to tf object-detection models is a list of tensors, hence NDList
            NDArray array = input.toNDArray(ctx.getNDManager(), Image.Flag.COLOR);
            // optionally resize the image for faster processing
            array = NDImageUtils.resize(array, 224);
            // tf object-detection models expect 8 bit unsigned integer tensor
            array = array.toType(DataType.UINT8, true);
            array = array.expandDims(0); // tf object-detection models expect a 4 dimensional input
            return new NDList(array);
        }

        @Override
        public void prepare(NDManager manager, Model model) throws IOException {
            if (classes == null) {
                String synsetLink = "https://raw.githubusercontent.com/tensorflow/models/master/research/object_detection/data/mscoco_label_map.pbtxt";
                URL synsetUrl = new URL(synsetLink);
                classes = new ConcurrentHashMap<>();
                int maxId = 0;
                try (InputStream is = synsetUrl.openStream();
                     Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                    scanner.useDelimiter("item ");
                    while (scanner.hasNext()) {
                        String content = scanner.next();
                        content = content.replaceAll("(\"|\\d)\\n\\s", "$1,");
                        Item item = JsonUtils.GSON.fromJson(content, Item.class);
                        classes.put(item.id, item.displayName);
                        if (item.id > maxId) {
                            maxId = item.id;
                        }
                    }
                }
            }
        }

        @Override
        public Classifications processOutput(TranslatorContext ctx, NDList list) {
            // output of tf object-detection models is a list of tensors, hence NDList in djl
            // output NDArray order in the list are not guaranteed

            int[] classIds = null;
            float[] probabilities = null;
            for (NDArray array : list) {
                if ("detection_scores".equals(array.getName())) {
                    probabilities = array.get(0).toFloatArray();
                } else if ("detection_classes".equals(array.getName())) {
                    // class id is between 1 - number of classes
                    classIds = array.get(0).toType(DataType.INT32, true).toIntArray();
                }
            }
            Objects.requireNonNull(classIds);
            Objects.requireNonNull(probabilities);

            List<String> retNames = new ArrayList<>();
            List<Double> retProbs = new ArrayList<>();

            // result are already sorted
            for (int i = 0; i < classIds.length; ++i) {
                int classId = classIds[i];
                double probability = probabilities[i];
                // classId starts from 1, -1 means background
                if (classId > 0 && probability > threshold) {
                    String className = classes.getOrDefault(classId, "#" + classId);
                    retNames.add(className);
                    retProbs.add(probability);
                }
            }

            return new Classifications(retNames, retProbs);
        }

        @Override
        public Batchifier getBatchifier() {
            return null;
        }
    }
}
