package sample;


import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Bird extends Pane
{
    public Point2D velocity; // 2D-геометрическая точка
    Rectangle rect;
    public Bird()
    {
        rect=new Rectangle(20,20,Color.RED); //моделька птицы
        velocity= new Point2D(0,0);
        setTranslateX(100); // координаты панельки, а не прямоугольника !!!
        setTranslateX(300);
        getChildren().addAll(rect);
    }

    public void moveY( int value)
    {
        boolean moveDown=value>0? true:false; // <0- движемся вниз, >0 прыгаем вверх
        for(int i=0;i<Math.abs(value);i++)
        {
            for(Wall w: Main.walls) // проходим по всем стенкам
            {
                if (this.getBoundsInParent().intersects(w.getBoundsInParent())) // если границы птички пересекает границы стенки
                {
                    if (moveDown) // если падаем вниз
                    {
                        setTranslateY(getTranslateY() - 1); // что бы не провалиться вниз
                        return;
                    } else // идем вверх
                    {
                        setTranslateY(getTranslateY() + 1); // что бы не задевать вверх
                        return;
                    }
                }
            }
            if (getTranslateY()<0) // что бы не уходил за границы экрана
            {
                setTranslateY(0);
            }
            if (getTranslateY()>580)
            {
                setTranslateY(580);
            }
            setTranslateY(getTranslateY()+(moveDown?1:-1)); // перемещение
        }


    }

    public void moveX(int value) // движение по горизонтали
    {
        for(int i=0;i<value;i++)
        {
            setTranslateX(getTranslateX()+1);
            for(Wall w: Main.walls)
            {
                if (this.getBoundsInParent().intersects(w.getBoundsInParent()))
                {
                    if(getTranslateX()+20==w.getTranslateX())
                    {
                        setTranslateX(getTranslateX()-1);
                        return;
                    }
                }

                if(getTranslateX()==w.getTranslateX())
                {
                    Main.score++;
                }

            }

        }

    }


    public void jump() // прыгаем на 3 по горизонтале и 15 вверх
    {
        velocity= new Point2D(3,-15);
    }

}

