import java.awt.*; //Color ��� ���� ���� awt ��Ű�� ����

public class Column extends Canvas {

Nemonemo parent; //Nemonemo Ŭ������ ��ü�� ����
	
	Image offScr; //������۸��� ���� ���� ȭ��
	Graphics offG;
	
	public Column(Nemonemo parent) //Nemonemo Ŭ������ ��ü�� �����ϰ� ��� ���� ������ '1'�� ������ ���
	{
		this.parent = parent; //Nemonemo Ŭ������ ��ü�� ����
		getColumn();
	}
	
	public void getColumn() //�����Ϳ� ���� column�� ���ڸ� ����
	{
		for(int i=0; i<10; i++) //��� ���� ������ '1'�� ������ ���
		{
			parent.numOfColumn[i] = getNumber(i);
		}
	}
		
	int getNumber(int start) //�ش��ϴ� ���� ������ '1'�� ������ ���
	{
		int count = 0; //���ӵ� '1'�� ����
		int pos = 0; //�� ��° ���ӵ� '1'�� ������ ��Ÿ���� �������� ǥ��
			
		for(int i = start; i<100; i+=10) //���� ���� ���� data�� ���� ��
		{
			if(parent.data.charAt(i)=='0' && count>0) //�������� ���� ���('0'�� ���)
			{
				parent.columnNums[start][pos++] = count;
				count = 0;
			}
			else if(parent.data.charAt(i)=='1' && count>=0) //������ ���('1'�� ���)
			{
				count++;
			}
		}
			
		if(count>0)
			parent.columnNums[start][pos++] = count;
		if(pos==0)
			parent.columnNums[start][pos++] = 0;
			
		return pos;
	}
		
	public void paint (Graphics g) //ȭ�鿡 column�� ���
	{
		offScr = createImage(201, 121); //���� ȭ�� ����
		offG = offScr.getGraphics();
		if(parent.mouseX!=-1)
		{
			offG.setColor(Color.yellow);
			offG.fillRect(20*parent.mouseX, 0, 19, 120); //���콺 Ŀ���� �ִ� ���� ���
		}
		
		offG.setColor(Color.black);
			
		for(int i=0; i<10; i++)
		{
			offG.drawLine(i*20, 0, i*20, 220);
			for(int j=0; j<parent.numOfColumn[i]; j++) //���� ���
			{
				if(String.valueOf(parent.columnNums[i][j]).length()<2)
					offG.drawString(String.valueOf(parent.columnNums[i][j]), i*20+9, (100-parent.numOfColumn[i]*20+j*20)+39);
				else
					offG.drawString(String.valueOf(parent.columnNums[i][j]), i*20+1, (100-parent.numOfColumn[i]*20+j*20)+39);
			}
		}
				
		for(int i=0; i<=20*10; i+=20*5)
		{
			offG.drawLine(i-1, 0, i-1, 120);
			offG.drawLine(i+1, 0, i+1, 120);
		}
				
		offG.drawLine(200, 0, 200, 120);
		offG.drawLine(0, 120, 200, 120);
				
		g.drawImage(offScr, 0, 0, this);
	}
		
			
	public void update(Graphics g)
	{
		paint(g);
	}

}
