package info.androidhive.firebase.model;

import java.io.Serializable;

/**
 * Created by Victoria on 10-12-2016.
 */

/*public class Buttons  implements Serializable{

        private String nameButton;
        private String functionButton;

        public Buttons(String nameButton, String functionButton){
            this.nameButton = nameButton;
            this.functionButton = functionButton;
        }



        public String getName() {
            return nameButton;
        }

        public String getType() {
            return functionButton;
        }
}
*/


import java.io.Serializable;

public class Buttons extends Food implements Serializable {




    public Buttons(String name, int cost, int weight, String type) {
        super(name, cost, weight, type);

    }

}
