import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JLabel;

public class Pipe
{
    static int Low = 200;  	//Starting range ofTop Rect
    static int High = 400; 	//Top Rect
    static int Low_2 = 180;    //Min Gap
    static int High_2 = 240;   //Max Gap
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

    	Random r=new Random();
    	x=1500+(gap_pipe*count_pipe);
    	int tmp=(r.nextInt(High - Low)/20)*20;
        top_end_y =  tmp+ Low;
        tmp=(r.nextInt(High_2 - Low_2)/20)*20;
        bottom_start_y = top_end_y+ tmp + Low_2;

    	L_top.setBounds(x ,top_end_y-Pipe.pipe_size_y, pipe_size_x, pipe_size_y);
        L_down.setBounds(x, bottom_start_y , pipe_size_x , pipe_size_y);

        count_pipe++;

    }
    
    public void newgame()
    {
    	Random r=new Random();
    	x=1100+(gap_pipe*count_pipe);
    	int tmp=(r.nextInt(High - Low)/20)*20;
        top_end_y =  tmp+ Low;
        tmp=(r.nextInt(High_2 - Low_2)/20)*20;
        bottom_start_y = top_end_y+ tmp + Low_2;

    	L_top.setBounds(x ,top_end_y-Pipe.pipe_size_y, pipe_size_x, pipe_size_y);
        L_down.setBounds(x, bottom_start_y , pipe_size_x , pipe_size_y);
        count_pipe++;
    }
}
