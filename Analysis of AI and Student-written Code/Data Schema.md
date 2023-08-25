| **Attribute** | **Description** | **Example Contents** (Strings) |
|---|---|---|
| source | The source of the generated solution - student or named AI. | "student", "gpt3.5", "bing", "bard" |
| milestone | The milestone task the solution relates to | "m0" |
| name | For the student solutions, this is the anonymised name.<br>For the AI solutions, this is a unique identifier. | "student102"<br>"4" |
| style | Only used for the AI generated solutions. The prompted style in which the solution was generated. May have no style, "clean code" or be "messy/inconsistently styled".  | "plain", "cc", "styled" |
| version | Only used for the Bing chat generated solutions. Refers to the version of Bing chat used to generate the solution. | "creative", "balanced", "concise" |
| file_name | Only relevant for the non-concatenated dataset. The name of the file the code solution was stored in. | "Exercise0.java" |
| code | The code solution for the task. | "public class Exercise0..." |
