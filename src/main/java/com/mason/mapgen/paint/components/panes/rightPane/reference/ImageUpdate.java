package com.mason.mapgen.paint.components.panes.rightPane.reference;

import java.awt.image.BufferedImage;

public interface ImageUpdate{


    void unsetImage();

    void setImage(BufferedImage image);


    interface ImageUpdateSetter{

        void setImageUpdate(ImageUpdate imageUpdate);

    }


    class ImageUpdateSlot implements ImageUpdate, ImageUpdateSetter{


        private ImageUpdate update = new ImageUpdate(){

            @Override
            public void unsetImage(){
                throw new IllegalStateException("ImageUpdate unset!");
            }

            @Override
            public void setImage(BufferedImage image){
                throw new IllegalStateException("ImageUpdate unset!");
            }

            @Override
            public String toString(){
                return "DEFAULT_ERROR_IMAGE_UPDATE";
            }
        };


        public ImageUpdateSlot(){}


        @Override
        public void unsetImage(){
            update.unsetImage();
        }

        @Override
        public void setImage(BufferedImage image){
            update.setImage(image);
        }

        @Override
        public void setImageUpdate(ImageUpdate imageUpdate){
            String updateName = this.update.toString();
            if(!updateName.equals("DEFAULT_ERROR_IMAGE_UPDATE")){
                throw new IllegalStateException("ImageUpdateSlot already filled!");
            }
            this.update = imageUpdate;
        }

    }

}
