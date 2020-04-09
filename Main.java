import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Main extends Application {
    static StanfordCoreNLP pipeline;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sentiment analysis");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // welcome text
        Text scenetitle = new Text("Sentiment Analysis");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        grid.add(scenetitle, 0, 0, 2, 1);

        // text box label
        Label boxLabel = new Label("Enter text to analyze:");
        boxLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        grid.add(boxLabel, 0, 1);

        // input field
        TextArea userTextField = new TextArea();
        grid.add(userTextField, 0, 2, 2, 3);

        // submit control
        Button btn = new Button("Analyze");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 6, 2, 1);

        // verdict
        Text verdict = new Text("Analysis Result");
        verdict.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        grid.add(verdict, 0, 7, 2, 1);

        // analysis result
        final Text actiontarget = new Text("Type some text and press the Anaylze button");
        grid.add(actiontarget, 0, 8);

        // button action
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setText(findSentiment(userTextField.getText()));
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void initSentiment() {
        pipeline = new StanfordCoreNLP("classifier.properties");
    }

    public static String findSentiment(String input) {
        int mainSentiment = 0;
        if (input != null && input.length() > 0)
        {
            int longest = 0;
            Annotation annotation = pipeline.process(input);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) 
            {
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest)
                {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        String sentiment[] = {"Very Negative", "Negative", "Neutral", "Positive", "Very Positive"};
        return sentiment[mainSentiment];
    }

    public static void main(String[] args) {
        initSentiment();
        System.out.println(findSentiment("I am very happy"));
        launch();
    }
}
