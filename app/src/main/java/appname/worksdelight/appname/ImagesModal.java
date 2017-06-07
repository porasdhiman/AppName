package appname.worksdelight.appname;

public class ImagesModal {
	 //private variables
    int _id;


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    int image;

     
    // Empty constructor
    public ImagesModal(){
         
    }
    // constructor
    public ImagesModal(int image){

        this.image = image;

    }
     
    // constructor
    public ImagesModal(int image, String ques_status){
        this.image = image;

    }

    // getting ID
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}

}
