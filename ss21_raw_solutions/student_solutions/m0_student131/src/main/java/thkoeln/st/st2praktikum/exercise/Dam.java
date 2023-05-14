package thkoeln.st.st2praktikum.exercise;

public class Dam {


    Position from, to;

    public Dam() {}


    public Dam(Position from, Position to) {
            this.from = from;
            this.to = to;
        }

        public Position getFrom () {
            return from;
        }

        public void setFrom (Position from){
            this.from = from;
        }

        public Position getTo () {
            return to;
        }

        public void setTo (Position to){
            this.to = to;
        }
    }