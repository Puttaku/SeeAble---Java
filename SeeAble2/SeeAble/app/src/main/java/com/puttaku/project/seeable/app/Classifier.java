package com.puttaku.project.seeable.app;

import android.graphics.Bitmap;

import java.util.List;

public interface Classifier {

    class Recognition {

//        private final String id;
        private final String title;
        private final Float confidence;

        public Recognition(final String title,final Float confidence) {
//            this.id = id;
            this.title = title;
            this.confidence = confidence;
        }

//        public String getId() {
//            return id;
//        }

        public String getTitle() {
            return title;
        }

//        public Float getConfidence() {
//            return confidence;
//        }

        @Override
        public String toString() {
            String resultString = "";
//            if (id != null) {
//                resultString = resultString + "[" + id + "] ";
//            }

            if (title != null) {
                if(confidence * 100.0f >= 40.0f){
                    resultString = resultString + title;
                }
                else{
                    resultString = resultString + "nothing";
                }
            }

//            if (confidence != null) {
//                resultString = resultString + String.format("(%.1f%%) ", confidence * 100.0f);
//            }

            return resultString;
        }
    }


    List<Recognition> recognizeImage(Bitmap bitmap);

    void close();
}
