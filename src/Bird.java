import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;

import javax.swing.*;

@SuppressWarnings("serial")
public class Bird extends JFrame implements Runnable ,MouseListener
{
    int r_top[]=new int[10];
    int r_down[]=new int[10];
    int b_x = 650;//x for bird
    int b_y = 160;//y for bird
    int st_bx=b_x,st_by=b_y;
    int bsize_x=50;
    int bsize_y=35;
    int total_x;
    boolean start=false;
    boolean gameover=false;
    int curpipe=0;
    
	Pipe P[]=new Pipe[5];
	JPanel over=new JPanel(null);
    JLabel bird=new JLabel("");
    JLabel bgl=new JLabel("");
    JLabel score=new JLabel("0",SwingConstants.CENTER);
    JLabel startB=new JLabel("Play");
    JLabel overhead=new JLabel("Gameover",SwingConstants.CENTER);
    JLabel oversc=new JLabel("Score",SwingConstants.CENTER);
    JLabel overbest=new JLabel("Highscore",SwingConstants.CENTER);
    
    static Thread T,B;
    int clicked=0;

    static ImageIcon bg,up,down,ibird,play;
    Font F=new Font("Courer",Font.BOLD,40);
    Font Fs=new Font("Courer",Font.BOLD,20);
	static BufferedReader br;
	static BufferedWriter bw;
	
    Bird()
    {
	    this.setTitle("My Bird");
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
			IS=this.getClass().getResourceAsStream("/images/play.png");
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
	    	    
	    score.setBounds(650,20,50,50);
	    score.setFont(F);
	    score.setForeground(Color.WHITE);
	    add(score);

	    for(int i=0;i<5;i++)
	    {
	    	P[i]=new Pipe();
	    	add(P[i].L_top);
	    	add(P[i].L_down);
	    	P[i].L_top.setIcon(Bird.up);
	    	P[i].L_down.setIcon(Bird.down);
	    }
	    
	    total_x=this.getHeight();
	    
	    bird.setBounds(b_x,b_y ,bsize_x,bsize_y);
	    add(bird);
	    bird.setIcon(ibird);
//	    bird.setBorder(new LineBorder(new Color(0,0,255)));
//	    bird.setOpaque(true);
//	    bird.setBackground(Color.white);

	    
	    startB.setBounds(625,450,100,45);
	    startB.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    add(startB,0);
	    startB.setIcon(play);
	    startB.addMouseListener(this);

	    over.setBounds(500,300,350,140);
	    over.setBackground(Color.darkGray);
	    
	    overhead.setBounds(0,0,350,50);
	    overhead.setForeground(Color.green);
	    overhead.setAlignmentX(Component.CENTER_ALIGNMENT);
	    overhead.setFont(F);
	    over.add(overhead);
	    
	    oversc.setBounds(0,60,350,30);
	    oversc.setForeground(Color.green);
	    oversc.setAlignmentX(Label.CENTER);
	    oversc.setFont(Fs);
	    over.add(oversc);
	    
	    overbest.setBounds(0,100,350,30);
	    overbest.setForeground(Color.green);
	    overbest.setAlignmentX(Label.CENTER);
	    overbest.setFont(Fs);
	    over.add(overbest);
	    
	    add(over,0);
	    over.setVisible(false);

	    bgl.setBounds(0, 0,1500,750);
	    add(bgl);
	    bgl.setIcon(bg);
	    this.addMouseListener(this);
	    
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
	        while(!gameover)
	        {
	            for(int i=0;i<Pipe.count_pipe;i++)
	            {
	            	P[i].x-=10;
	            	
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
	                Thread.sleep(50);
	            }
	            catch (InterruptedException ex)
	            {
	            	System.out.println("Error in run: "+ex);
	            }
	           
	        }//while true
    		}
    	}//if label
    	else if(Thread.currentThread().getName().equals("bird"))
    	{
    		
            float tmpbx=0,tmpby=0;
    		while(true)
    		{
            while(!gameover)
    		{
    			
    			System.out.println("b_x : "+(b_x+bsize_x)+" P[curpipe].x : "+P[curpipe].x+" - "+(P[curpipe].x+Pipe.pipe_size_x));
    			if(b_y+bsize_y>=735)
    			{
    				b_y=735-bsize_y;
    				gameover=true;
    			}
    			else if(b_y<=0)
    			{
    				b_y=0;
    				gameover=true;    				
    			}
    			
    			if(b_x+bsize_x>=P[curpipe].x && b_x<=(P[curpipe].x+Pipe.pipe_size_x))
    			{
    				System.out.println("Pass");
    				System.out.println("b_y:"+b_y+" P : "+P[curpipe].top_end_y+" - "+P[curpipe].bottom_start_y);
    				if(b_y<=P[curpipe].top_end_y)
    				{
    					b_y=P[curpipe].top_end_y;
    					gameover=true;
    					System.out.println("Gameover");
    				}
    				else if(b_y+bsize_y>=P[curpipe].bottom_start_y)
    				{
    					b_y=P[curpipe].bottom_start_y-bsize_y;
    					gameover=true;
    					System.out.println("Gameover");   				
    				}
    			}
    			if(b_x>(P[curpipe].x+Pipe.pipe_size_x))
    			{
    				curpipe++;
    				if(curpipe==5)
    					curpipe=0;
    				
    				score.setText((Integer.parseInt(score.getText())+1)+"");
    			}
    			if(!gameover)
    			{
					if(clicked!=0)
					{
						if(clicked==100)
							tmpbx=0;
						tmpby=0;
		                tmpbx+=4;
			            /*here apply logical decrement in x and y for going up bird */
			            b_y=b_y-20;
			            /*here apply logical decrement in x and y for going up bird */
			            clicked-=20;
			            try
			            {
			                Thread.sleep((long) (50+tmpbx));
			            }
			            catch (InterruptedException ex)
			            {
			            	System.out.println("Error in run: "+ex);
			            }

					}
					else
					{
						tmpbx=0;
		                tmpby+=0.5;
			            /*here apply logical decrement in x and y for going down bird */
			            b_y+=20;
			            /*here apply logical decrement in x and y for going down bird */
			            try
			            {
			                Thread.sleep((long) (70-tmpby));
			            }
			            catch (InterruptedException ex)
			            {
			            	System.out.println("Error in run: "+ex);
			            }

					}
    			}
    			else
    			{
    				tmpbx=0;
    				tmpby=0;
    				score.setVisible(false);
    				int scr=Integer.parseInt(score.getText());
    				oversc.setText("Score : "+scr);
    				
    				try
    				{
	    				br = new BufferedReader(new FileReader("bin//data//high.txt"));
	    				String tmp=br.readLine();
	    				br.close();
	    				int bst=Integer.parseInt(tmp);
	    				if(scr>bst)
	    				{
	    					bst=scr;
	    					bw = new BufferedWriter(new FileWriter("bin//data//high.txt"));
	    					bw.write(scr+"\n");
	    					bw.close();
	    				}
	    				overbest.setText("Best : "+bst);
    				}
    				catch(Exception e)
    				{
    					System.out.println("Error in Reding file");
    					e.printStackTrace();
    				}
    				

    				over.setVisible(true);
    				startB.setVisible(true);
    			}
	    	    bird.setBounds(b_x,b_y ,bsize_x,bsize_y);
    		}//while true
    		}
    	}//else if Bird
    	else
    	{
    		System.out.println("Problem in run()");
    	}
    }

	public void mouseClicked(MouseEvent e)
	{
		if(e.getSource()==startB)
		{
			startB.setVisible(false);
			if(!start)
			{
				T=new Thread(this,"label");
			    T.start();
			    B=new Thread(this,"bird");
			    B.start();
				start=true;

			}
			else
			{
				over.setVisible(false);
				score.setVisible(true);
				b_x=st_bx;
				b_y=st_by;
				Pipe.count_pipe=0;
				for(int i=0;i<5;i++)
				{
					P[i].newgame();
				}
				curpipe=0;
				clicked=0;
				score.setText("0");
				gameover=false;				
			}
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
