import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;

import javax.swing.*;

@SuppressWarnings("serial")
public class Bird extends JFrame implements Runnable ,MouseListener
{
    int r_top[]=new int[10];
    int r_down[]=new int[10];
    int b_x = 600;//x for bird
    int b_y = 100;//y for bird
    int birdsize=50;
    int total_x;
    boolean start=false;
    
	Pipe P[]=new Pipe[5];
    JLabel bird=new JLabel("");
    JLabel bgl=new JLabel("");
    
    static Thread T,B;
    int clicked=0;

    static ImageIcon bg,up,down,ibird,play;
    
    Bird()
    {
	    this.setTitle("My Bird");
	    //System.out.println(this.getMinimumSize()tWidth()+":"+this.getHeight());
	    this.setSize(this.getMaximumSize());
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    this.setLayout(null);

		InputStream IS=null;
		Image IG=null;
		try
		{
			IS=this.getClass().getResourceAsStream("/images/background.png");
			if(IS!=null)
			{
				IG=(Image)javax.imageio.ImageIO.read(IS);
				bg=new ImageIcon(IG);
			}
			IS=this.getClass().getResourceAsStream("/images/pipe_up.png");
			if(IS!=null)
			{
				IG=(Image)javax.imageio.ImageIO.read(IS);
				up=new ImageIcon(IG);
			}
			IS=this.getClass().getResourceAsStream("/images/pipe_down.png");
			if(IS!=null)
			{
				IG=(Image)javax.imageio.ImageIO.read(IS);
				down=new ImageIcon(IG);
			}
			IS=this.getClass().getResourceAsStream("/images/bird_small.png");
			if(IS!=null)
			{
				IG=(Image)javax.imageio.ImageIO.read(IS);
				ibird=new ImageIcon(IG);
			}
			IS=this.getClass().getResourceAsStream("/images/playbutton.png");
			if(IS!=null)
			{
				IG=(Image)javax.imageio.ImageIO.read(IS);
				play=new ImageIcon(IG);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in images: "+e);
		}
	    	    
	    System.out.println(":>"+bg);
	    System.out.println(":"+up);
	    System.out.println("::"+down);
	    System.out.println(":::"+ibird);


	    for(int i=0;i<5;i++)
	    {
	    	P[i]=new Pipe();
	    	add(P[i].L_top);
	    	add(P[i].L_down);
	    }
	    
	    total_x=this.getHeight();
	    
	    bird.setBounds(b_x,b_y ,birdsize,birdsize);
	    bird.setIcon(ibird);
	    bird.setOpaque(false);
	    add(bird);
	    this.addMouseListener(this);
	    
	    bgl.setIcon(bg);
	    bgl.setBounds(0, 0,1500,750);
	    add(bgl);

    }

    public static void main(String[] args)
    {
    	new Bird(); 
    }
   
    public void run()
    {
    	if(Thread.currentThread().getName().equals("label"))
    	{
	        while(true)
	        {
	            for(int i=0;i<Pipe.count_pipe;i++)
	            {
	            	P[i].x-=20;
	            	
	            	/*== Start :: when pipe reach at 0 , Pipe's x reset ==*/
	            	if(P[i].x< (-Pipe.pipe_size_x) )
	            	{
	            		int j;
	            		if(i==0)
	            			j=Pipe.count_pipe-1;
	            		else
	            			j=i-1;
	            		
	            		P[i].x=Pipe.gap_pipe+P[j].x;
	            	}
	            	/*== End :: when pipe reach at 0 , Pipe's x reset ==*/

		            P[i].L_top.setBounds(P[i].x ,P[i].top_end_y-Pipe.pipe_size_y, Pipe.pipe_size_x, Pipe.pipe_size_y);
		            P[i].L_down.setBounds(P[i].x, P[i].bottom_start_y , Pipe.pipe_size_x ,Pipe.pipe_size_y);
	            }
		            
	            try
	            {
	                Thread.sleep(100);
	            }
	            catch (InterruptedException ex)
	            {
	            	System.out.println("Error in run: "+ex);
	            }
	           
	        }//while true
    	}//if label
    	else if(Thread.currentThread().getName().equals("bird"))
    	{
            int tmpby=0;
    		while(true)
    		{
    			if(clicked!=0)
    			{
                            tmpby=0;
		            /*here apply logical decrement in x and y for going up bird */
		            b_y-=50;
		            /*here apply logical decrement in x and y for going up bird */
		            clicked-=100;
    			}
    			else
    			{
                    tmpby+=2;
		            /*here apply logical decrement in x and y for going down bird */
		            b_y+=10+tmpby;
		            /*here apply logical decrement in x and y for going down bird */
    			}
    			
	            try
	            {
	                Thread.sleep(100);
	            }
	            catch (InterruptedException ex)
	            {
	            	System.out.println("Error in run: "+ex);
	            }
	    	    bird.setBounds(b_x,b_y ,birdsize,birdsize);
    		}//while true
    	}//else if Bird
    	else
    	{
    		System.out.println("Problem in run()");
    	}
    }

	public void mouseClicked(MouseEvent e)
	{
		if(!start)
		{
			T=new Thread(this,"label");
		    T.start();
		    B=new Thread(this,"bird");
		    B.start();
			start=true;
		}
	}

	public void mousePressed(MouseEvent e)
	{
		
	}

	public void mouseReleased(MouseEvent e)
	{
           clicked=100;
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}

	public void mouseExited(MouseEvent e)
	{
		
	}
}
