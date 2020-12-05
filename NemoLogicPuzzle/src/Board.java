import java.awt.*; //Color ��� ���� ���� awt ��Ű�� ����
import java.awt.event.*;

public class Board extends Canvas implements MouseListener, MouseMotionListener
{
	Nemonemo parent; //Nemonemo Ŭ������ ��ü�� ����
	boolean drag = false; //���콺 �巡�� �������� ����
	int startX, startY; //���콺 �巡�׸� ������ ��ǥ
	int endX, endY; //���콺 �巡�׸� ����ģ ��ǥ
	
	Image offScr; //������۸��� ���� ���� ȭ��
	Graphics offG;
	
	public Board(Nemonemo parent) //Nemonemo Ŭ������ ��ü�� �����ϰ� �����ʸ� ����
	{
		this.parent = parent; //Nemonemo Ŭ������ ��ü�� ����
		this.addMouseListener(this); //���콺 ����� ���� ������ ����
		this.addMouseMotionListener(this);
	}
	
	public void paint (Graphics g) //ȭ�鿡 ������ ���¸� ���
	{
		offScr = createImage(201, 201); //���� ȭ�� ����
		offG = offScr.getGraphics();
		
		for(int j=0; j<10; j++)
			for(int i=0; i<10; i++)
			{
				if(parent.endFlag) //������ ���� ���
				{
					if(parent.data.charAt(j*10+i)=='1')
					{
					offG.fillRect(i*20, j*20, 20, 20); //ĭ�� ä���� ������ Ǯ������ ǥ��
					}
				}
				else
				{
					if(parent.temp[j*10+i]==1)
					{
						offG.setColor(Color.blue); //���� �������� ���� ��ǥ��
						offG.fillOval(i*20, j*20, 20, 20);
					}
					else if(parent.temp[j*10+i]==2)
					{
						offG.setColor(Color.red); //���� �������� ���� Xǥ��
						offG.drawLine(i*20, j*20, i*20+20, j*20+20);
						offG.drawLine(i*20, j*20+20, i*20+20, j*20);
					}
				}
			}
		if(drag) //���콺�� �巡���� ���
		{
			offG.setColor(Color.yellow);
			if(startX==endX)
			{
				if(startY<endY)
				{
					offG.fillRect(20*startX, 20*startY, 20, 20*(endY-startY+1));
					offG.setColor(Color.red);
					offG.drawString(String.valueOf(endY-startY+1), endX*20+2, (endY+1)*20-2);
				}
				else
				{
					offG.fillRect(20*endX, 20*endY, 20, 20*(startY-endY+1));
					offG.setColor(Color.red);
					offG.drawString(String.valueOf(startY-endY+1), endX*20+2, (endY+1)*20-2);
				}
			}
			else if(startY==endY)
			{
				if(startX<endX)
				{
					offG.fillRect(20*startX, 20*startY, 20*(endX-startX+1), 20);
					offG.setColor(Color.red);
					offG.drawString(String.valueOf(endX-startX+1), endX*20+2, (endY+1)*20-2);
				}
				else
				{
					offG.fillRect(20*endX, 20*endY, 20*(startX-endX+1), 20);
					offG.setColor(Color.red);
					offG.drawString(String.valueOf(startX-endX+1), endX*20+2, (endY+1)*20-2);	
				}
			}
		}
		for(int j=0; j<10; j++) //���� ���
			for(int i=0; i<10; i++)
			{
				offG.setColor(Color.black);
				offG.drawRect(i*20, j*20, 20, 20);
			}
		offG.setColor(Color.black);
		
		for(int i=0; i<=200; i+=20*5)
		{
			offG.drawLine(i-1, 0, i-1, 200);
			offG.drawLine(i+1, 0, i+1, 200);
		}
		
		for(int i=0; i<=200; i+=20*5)
		{
			offG.drawLine(0, i-1, 200, i-1);
			offG.drawLine(0, i+1, 200, i+1);
		}
		
		g.drawImage(offScr, 0, 0, this); //���� ȭ���� ���� ȭ������ ����
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
	@Override
	public void mousePressed(MouseEvent e) //�÷��̾ ���콺 ��ư�� ���� ���
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x/20)>=10) return;
		if((y/20)>=10) return;
		if(parent.endFlag)return;
		
		startX = x/20;
		startY = y/20;
	}
	public void mouseReleased(MouseEvent e) //�÷��̾ ���콺 ��ư�� ���� ���
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x/20)>=10) return;
		if((y/20)>=10) return;
		if(parent.endFlag)return;
		
		if((e.getModifiers() & InputEvent.BUTTON3_MASK)!=0) //���콺 ������ ��ư
		{
			setTemp(x, y, 2);
		}
		else //���콺 ���� ��ư 
		{
			setTemp(x, y, 1);
		}
		
		parent.display(); //������ Ǯ�ȴ��� �˻�
		this.drag = false;
		repaint();
	}
	public void mouseMoved(MouseEvent e) //���콺�� ������ ���
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x/20)>=10) return;
		if((y/20)>=10) return;
		
		parent.showLocation(x/20, y/20); //column�� row�� ���콺 Ŀ���� ��ġ�� ǥ��
		repaint();		
	}
	public void mouseExited(MouseEvent e) //���콺�� ���带 ��� ���
	{
		parent.showLocation(-1, -1); //column�� row�� ���콺 Ŀ���� ��ġ�� ǥ������ ����
		this.drag = false;
		repaint();	
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) //���콺�� �巡���� ���
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x/20)>=10) return;
		if((y/20)>=10) return;
		
		parent.showLocation(x/20, y/20); //column�� row�� ���콺 Ŀ���� ��ġ�� ǥ��		
		this.drag = true;
		endX = x/20;
		endY = y/20;
		repaint();	
	}
	private void setTemp(int x, int y, int value)  
        //�÷��̾��� �Է��� temp �迭�� ����
	{
		int i;
		
		if(drag)
		{
			if(startX==endX)
			{
				if(startY<endY)
				{
					for(i=startY; i<=endY; i++)
					{
						parent.temp[startX+i*10] = value;
					}
				}
				else if(startY>endY)
				{
					for(i=endY; i<=startY; i++)
					{
						parent.temp[startX+i*10] = value;
					}
				}
				else
				{
					if(parent.temp[startX+startY*10]!=0)
						parent.temp[startX+startY*10] = 0;
					else
						parent.temp[startX+startY*10] = value;
				}
			}
			else if(startY==endY)
			{
				if(startX<endX)
				{
					for(i=startX; i<=endX; i++)
					{
						parent.temp[i+startY*10] = value;
					}
				}
				else if(startX>endX)
				{
					for(i=endX; i<=startX; i++)
					{
						parent.temp[i+startY*10] = value;
					}
				}
				else
				{
					if(parent.temp[startX+startY*10]!=0)
						parent.temp[startX+startY*10] = 0;
					else
						parent.temp[startX+startY*10] = value;
				}
			}
		}
		else
		{
			if(parent.temp[x/20+y/20*10]!=0)
				parent.temp[x/20+y/20*10] = 0;
			else
				parent.temp[x/20+y/20*10] = value;
		}
	}


}
