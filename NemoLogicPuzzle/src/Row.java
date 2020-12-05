import java.awt.*; //Color ��� ���� ���� awt ��Ű�� ����

public class Row extends Canvas{
	Nemonemo parent; //Nemonemo Ŭ������ ��ü�� ����
	Image offScr; //������۸��� ���� ���� ȭ��
	Graphics offG;
	
	public Row(Nemonemo parent) //Nemonemo Ŭ������ ��ü�� �����ϰ� ��� ���� ������ '1'�� ������ ���
	{ 
		this.parent = parent; //Nemonemo Ŭ������ ��ü�� ����
		getRow(); 
	}
	
	public void getRow() //�����Ϳ� ���� row�� ���ڸ� ����
	{
		for(int i=0; i<10; i++) //��� �࿡ ������ '1'�� ������ ���
		{
			parent.numOfRow[i] = getNumber(i);
		}
	}
		
	int getNumber(int start) //�ش��ϴ� ���� ������ '1'�� ������ ���
	{
		int count = 0; //���ӵ� '1'�� ����
		int pos = 0; //�� ��° ���ӵ� '1'�� ������ ��Ÿ���� �������� ǥ��
			
		for(int i=start*10; i<(start+1)*10; i++) //���� �࿡ ���� data�� ���� ��
		{
			if(parent.data.charAt(i)=='0' && count>0) //�������� ���� ���('0'�� ���)
			{
				parent.rowNums[start][pos++] = count;
				count = 0;
			}
			else if(parent.data.charAt(i)=='1' && count>=0) //������ ���('1'�� ���)
			{
				count++;
			}
		}
			
		if(count>0)
			parent.rowNums[start][pos++] = count;
		if(pos==0)
			parent.rowNums[start][pos++] = 0;
			
		return pos;
	}
		
	public void paint (Graphics g) //ȭ�鿡 row�� ���
	{
		offScr = createImage(121, 201); //���� ȭ�� ����
		offG = offScr.getGraphics();
		if(parent.mouseY!=-1)
		{
			offG.setColor(Color.yellow);
			offG.fillRect(0, 20*parent.mouseY, 120, 19); //���콺 Ŀ���� �ִ� ���� ���
		}
		
		offG.setColor(Color.black);
			
		for(int i=0; i<10; i++)
		{
			offG.drawLine(0, i*20, 120, i*20);
			for(int j=0; j<parent.numOfRow[i]; j++) //���� ���
			{			if(String.valueOf(parent.rowNums[i][j]).length()<2)
					offG.drawString(String.valueOf(parent.rowNums[i][j]), (100-parent.numOfRow[i]*20)+j*20+27, i*20+18);
				else
					offG.drawString(String.valueOf(parent.rowNums[i][j]), (100-parent.numOfRow[i]*20)+j*20+21, i*20+18);
			}
		}
				
		for(int i=0; i<=20*10; i+=20*5)
		{
			offG.drawLine(0, i-1, 120, i-1);
			offG.drawLine(0, i+1, 120, i+1);
		}
				
		offG.drawLine(0, 200, 120, 200);
		offG.drawLine(120, 0, 120, 200);
				
		g.drawImage(offScr, 0, 0, this);
	}
		
			
	public void update(Graphics g)
	{
		paint(g);
	}


}
