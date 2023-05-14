package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    int x, y;
    int rechts = 1;
    int hoch = 6;
    int[][] board;


    @Override
    public String goTo(String goCommandString) {


        board = new int[rechts][hoch];


        String c = String.valueOf(goCommandString.charAt(4));
        int a = Integer.valueOf(c);
        String b = String.valueOf(goCommandString.charAt(1));

        if (b.equals("n")) {
            if (this.rechts == 0) {
                if (this.hoch == 5){if(a > 0){this.hoch=5;}}
                else if (this.hoch == 4){if(a >= 1){this.hoch=5;}}
                else if (this.hoch == 3){if(a >= 2) { this.hoch = 5;}}
                else if (this.hoch == 2){if(a >= 3){this.hoch = 5;}}
                else if (this.hoch == 1){if(a >= 4){this.hoch = 5;}}
                else if (this.hoch == 0){if(a >= 5){this.hoch = 5;}}
                else {
                    this.hoch = hoch + a;
                }}

            else if (this.rechts == 1){
                if(this.hoch == 5){if (a >= 0){this.hoch = 5;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 1){
                if(this.hoch == 4){if(a >= 0){this.hoch = 4;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 2){
                if(this.hoch == 4){if(a > 0){this.hoch = 4;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 3){
                if(this.hoch == 4){if(a > 0){this.hoch = 4;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 4){
                if(this.hoch == 4){if(a > 0){this.hoch = 5;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 5){
                if(this.hoch == 4){if(a > 0){this.hoch = 5;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 6){
                if(this.hoch == 4){if(a > 0){this.hoch = 5;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 7){
                if(this.hoch == 4){if(a >= 0){this.hoch = 4;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}
            else if (this.rechts == 8){
                if(this.hoch == 4){if(a >= 0){this.hoch = 4;}}
                else if (this.hoch == 3){if (a >= 1){this.hoch = 4;}}
                else if (this.hoch == 2){if (a >= 2){this.hoch = 4;}}
                else if (this.hoch == 1){if (a >= 3){this.hoch = 4;}}
                else if (this.hoch == 0){if (a >= 4){this.hoch = 4;}}
                else {
                    this.hoch = hoch + a;
                }}

            else {
                this.hoch = hoch + a;
            }
            if (this.hoch >= 7) {
                this.hoch = 7; }}

        if (b.equals("s")) {
            if (this.rechts == 0) {
                if (this.hoch == 7) { if (a >= 1) { this.hoch = 6; } }
                else if (this.hoch == 6) { if (a >= 0) { this.hoch = 6; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 1) {
                if (this.hoch == 7) { if (a >= 1) { this.hoch = 6; } }
                else if (this.hoch == 6) { this.hoch = 6; }
                else if (this.hoch == 5) { this.hoch = 5; }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 2) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6){if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5){if (a >= 0){this.hoch = 5;}}
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 3) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 4) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 5) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 6) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 7) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else if (this.rechts == 8) {
                if (this.hoch == 7) { if (a >= 2) { this.hoch = 5; } }
                else if (this.hoch == 6) { if (a >= 1) { this.hoch = 5; } }
                else if (this.hoch == 5) { if (a >= 0) { this.hoch = 5; } }
                else {
                    this.hoch = hoch - a;
                }}
            else {
                this.hoch = hoch - a;
            }
            if (this.hoch <= 0) {
                this.hoch = 0;
            }
        }

        if (b.equals("e")) {
            if(this.rechts==0) {
                if(this.hoch== 1){if(a >= 8){this.rechts = 8;}}
                if(this.hoch== 2){if(a >= 8){this.rechts = 8;}}
                if(this.hoch== 3){if(a >= 8){this.rechts = 8;}}
                if(this.hoch== 4){if(a >= 8){this.rechts = 8;}}
                if(this.hoch== 6){if(a >= 2){this.rechts = 2;}}
                if(this.hoch== 7){if(a >= 2){this.rechts = 2;}}}

            else if(this.rechts==1) {
                if(this.hoch== 1){if(a >= 7){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 7){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 7){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 7){this.rechts = 8;}}
                else if(this.hoch== 6){if(a >= 1){this.rechts = 2;}}
                else if(this.hoch== 7){if(a >= 1){this.rechts = 2;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==2) {
                if(this.hoch== 1){if(a >= 6){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 6){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 6){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 6){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==3) {
                if(this.hoch== 1){if(a >= 5){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 5){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 5){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 5){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==4) {
                if(this.hoch== 1){if(a >= 4){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 4){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 4){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 4){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==5) {
                if(this.hoch== 1){if(a >= 3){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 3){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 3){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 3){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==6) {
                if(this.hoch== 1){if(a >= 2){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 2){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 2){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 2){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==7) {
                if(this.hoch== 1){if(a >= 1){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 1){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 1){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 1){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else if(this.rechts==8) {
                if(this.hoch== 1){if(a >= 0){this.rechts = 8;}}
                else if(this.hoch== 2){if(a >= 0){this.rechts = 8;}}
                else if(this.hoch== 3){if(a >= 0){this.rechts = 8;}}
                else if(this.hoch== 4){if(a >= 0){this.rechts = 8;}}
                else {
                    this.rechts = rechts + a;}}

            else {
                this.rechts = rechts + a;}

            if (this.rechts >= 10) {
                this.rechts = 10; }}

        if (b.equals("w")) {
            if(this.rechts==10) {
                if(this.hoch== 1){if(a >= 1){this.rechts = 9;}}
                else if(this.hoch== 2){if(a >= 1){this.rechts = 9;}}
                else if(this.hoch== 3){if(a >= 1){this.rechts = 9;}}
                else if(this.hoch== 4){if(a >= 1){this.rechts = 9;}}
                else if(this.hoch== 6){if(a >= 7){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 7){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==9) {
                if(this.hoch== 1){if(a >= 0){this.rechts = 9;}}
                else if(this.hoch== 2){if(a >= 0){this.rechts = 9;}}
                else if(this.hoch== 3){if(a >= 0){this.rechts = 9;}}
                else if(this.hoch== 4){if(a >= 0){this.rechts = 9;}}
                else if(this.hoch== 6){if(a >= 6){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 6){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==8) {
                if(this.hoch== 6){if(a >= 5){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 5){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==7) {
                if(this.hoch== 6){if(a >= 4){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 4){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==6) {
                if(this.hoch== 6){if(a > 3){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 3){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==5) {
                if(this.hoch== 6){if(a > 2){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 2){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==4) {
                if(this.hoch== 6){if(a > 1){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 1){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}
            else if(this.rechts==3) {
                if(this.hoch== 6){if(a >= 0){this.rechts = 3;}}
                else if(this.hoch== 7){if(a >= 0){this.rechts = 3;}}
                else{
                    this.rechts = rechts - a;}}

            else{
                this.rechts = rechts - a;}
        }
        if (this.rechts <= 0) {
            this.rechts = 0;
        }




        return "(" + rechts + "," + hoch + ")";
    }
}



