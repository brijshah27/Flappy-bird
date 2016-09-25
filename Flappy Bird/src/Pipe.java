import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JLabel;

public class Pipe
{
    static int Low = 100;  	//Starting range ofTop Rect
    static int High = 500; 	//Top Rect
    static int Low_2 = 130;    //Min Gap
    static int High_2 = 200;   //Max Gap
    static int gap_pipe=400;
    static int count_pipe=0;
    
    JLabel L_top;
    JLabel L_down;
    
    int x=0;		//for both label top and down x is same...    
    int top_end_y=0;
    int bottom_start_y=0;
    static int pipe_size_x=50;
    static int pipe_size_y=600;
    
    Pipe()
    {
    	L_top=new JLabel("");
    	L_down=new JLabel("");
    	
    	L_top.setLayout(new BorderLayout());
    	L_down.setLayout(null);
    	
    	L_top.setIcon(Bird.up);
    	L_down.setIcon(Bird.down);
    	L_top.setOpaque(false);
    	L_down.setOpaque(false);
    	
    	Random r=new Random();
    	x=1000+(gap_pipe*count_pipe);
        top_end_y = r.nextInt(High - Low) + Low;
        bottom_start_y = top_end_y+ r.nextInt(High_2 - Low_2) + Low_2;

        count_pipe++;

    }
}
