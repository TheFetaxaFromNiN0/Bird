package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {
    public static Pane appRoot= new Pane(); // панель самого приложения
    public static Pane gameRoot= new Pane(); // поле, где расположены все стенки
    public static ArrayList <Wall> walls= new ArrayList<>(); // список стен, для проверки столкновений
    public static Bird bird=new Bird(); // сама птица
    public static int score=0; // счет
    public static Label scoreLabel= new Label("Score: "+score);

    public static Parent createContent() // отвечает за создание сцены приложения
    {
        gameRoot.setPrefSize(600,600); // устанавливаем размер приложения
        for(int i=0;i<100;i++) // создание столбцов
        {
            int enter=(int)(Math.random()*100+50); // проем в стенке
            int height = new Random().nextInt(600-enter); // сама стенка
            Wall wall= new Wall(height); // создаем стенку
            wall.setTranslateX(i*350+600); // через каждые 350 пикселей стена, +600- что не было видно следующей
            wall.setTranslateY(0); // стенка начинается сразу сверху
            walls.add(wall);

            Wall wall2= new Wall(600 - enter-height);
            wall2.setTranslateX(i*350+600);
            wall.setTranslateY(height+enter);
            walls.add(wall2);
            gameRoot.getChildren().addAll(wall,wall2);

        }




        gameRoot.getChildren().add(bird);
        appRoot.getChildren().addAll(gameRoot,scoreLabel);
        //appRoot.getChildren().addAll(gameRoot,scoreLabel);
        return  appRoot; // корневой узел

    }


    public void update() // вызывается каждый кадр
    {
        if(bird.velocity.getY()<5) // гравитация в игре
        {
            bird.velocity=bird.velocity.add(0,1);
        }

        bird.moveX((int)bird.velocity.getX()); // двигаем птичку
        bird.moveY((int)bird.velocity.getY());
        scoreLabel.setText("Score: "+score);


        bird.translateXProperty().addListener((obs,old,newValue)-> // движение сцены что бы не выходил из за кадра
        {
            int offset= newValue.intValue();
            if (offset>200)
                gameRoot.setLayoutX(-(offset-200)); // как персонаж пересек 200 пикселей , сцены движется влево

        });


    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
       Scene scene = new Scene(createContent()); // создаем сцену
        scene.setOnMouseClicked(event -> bird.jump()); // при клике на сцену птичка прыгает
        AnimationTimer timer= new AnimationTimer() // вызывается каждый кадр иг ры
        {
            @Override
            public void handle(long now)
            {
                update();
            }
        };
        timer.start();


        primaryStage.setScene(scene); // устанавливаем на форму
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
