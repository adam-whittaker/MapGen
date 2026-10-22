package com.mason.mapgen.paint.components.panes.rightPane.reference;

import java.awt.image.BufferedImage;

public interface ImageUpdate{


    void clearImage();

    void setImage(BufferedImage image);

    void recentreImage();


    interface ImageUpdateSetter{

        void setImageUpdate(ImageUpdate imageUpdate);

    }


    class ImageUpdateSlot implements ImageUpdate, ImageUpdateSetter{


        private ImageUpdate update = new ImageUpdate(){

            @Override
            public void clearImage(){
                throw new IllegalStateException("ImageUpdate unset!");
            }

            @Override
            public void setImage(BufferedImage image){
                throw new IllegalStateException("ImageUpdate unset!");
            }

            @Override
            public void recentreImage(){
                throw new IllegalStateException("ImageUpdate unset!");
            }

        };
        private boolean updateSet = false;


        public ImageUpdateSlot(){}


        @Override
        public void clearImage(){
            update.clearImage();
        }

        @Override
        public void setImage(BufferedImage image){
            update.setImage(image);
        }

        @Override
        public void recentreImage(){
            update.recentreImage();
        }

        @Override
        public void setImageUpdate(ImageUpdate imageUpdate){
            if(updateSet){
                throw new IllegalStateException("ImageUpdateSlot already filled!");
            }
            updateSet = true;
            this.update = imageUpdate;
        }

    }

}
