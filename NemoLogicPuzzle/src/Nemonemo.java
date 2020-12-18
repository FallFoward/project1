import javax.swing.*; //���� ��Ű�� ����
import java.awt.*; //Font ��� ���� ���� awt ��Ű�� ����
import java.awt.event.*;
import java.io.*;

public class Nemonemo extends JFrame implements ActionListener{
		JPanel contentPane;
		
		//�޴�
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help");
		
		//����(add)�� Ŭ������ ����
		Board board;
		Column col;
		Row row;
		
		//���콺 Ŀ���� ��ǥ
		int mouseX = -1;
		int mouseY = -1;
		
		String data = "0001000000011100001101010000101111111110111111111000011111100001111100000100010000010001000011001100"; //������ ����(�ʱ��:������)
		int[] temp; //�÷��̾ �Է��� ��
		
		int columnNums[][]; //�ش� ���� ������ '1'�� ������ ǥ��
		int numOfColumn[]; //'0'���� ������ ������ 1�� ������ �� ���ΰ��� ǥ��
		int rowNums[][]; //�ش� �࿡ ������ '1'�� ������ ǥ��
		int numOfRow[]; //'0'���� ������ ������ 1�� ������ �� ���ΰ��� ǥ��
		
		boolean endFlag = false; //������ Ǯ�ȴ��� ����
		
		public static void main(String args[]) //Nemonemo ���ø����̼� ������
		{
			Nemonemo nemo = new Nemonemo(); //�׸�׸���� ���� ����
			nemo.setVisible(true);
			nemo.toFront();
		}
		
			public Nemonemo()
			{
				this.setTitle("Nemonemo Logic"); //���ø����̼� â�� Ÿ��Ʋ ����
				this.setSize(341, 390); //���ø����̼� ũ�� ����
				
				//���� �ʱ�ȭ
				temp = new int[100]; //���� 10ĭ, ���� 10ĭ���� �� 100ĭ ����
				for(int i=0; i<100; i++) //�÷��̾ �Է��ϱ� ���� 0���� ��� �ʱ�ȭ
				{
					temp[i] = 0;
				}
				columnNums = new int[10][10];
				numOfColumn = new int[10];
				rowNums = new int[10][10];
				numOfRow = new int[10];
				
				contentPane = (JPanel) getContentPane();
				contentPane.setBackground(Color.white);
				contentPane.setLayout(null); //null ���̾ƿ����� ����
				 
				createMenus(); //�޴� ����
				
				//column ����
				col = new Column(this);
				contentPane.add(col);
				col.setFont(new Font("SansSerif", Font.BOLD, 14));
				col.setBounds(120, 0, 201, 120);
				col.repaint();
				
				//row ����
				row = new Row(this);
				contentPane.add(row);
				row.setFont(new Font("SansSerif", Font.BOLD, 14));
				row.setBounds(0, 120, 120, 201);
				
				//board ����
				board = new Board(this);
				contentPane.add(board);
				board.setFont(new Font("SansSerif", Font.BOLD, 14));
				board.setBounds(120, 120, 201, 201);
			}
			
			public void createMenus()
			{
				this.setJMenuBar(menuBar);
				menuBar.add(gameMenu);
				
				//Game �޴��� ���θ޴� ����
				JMenuItem newGame = new JMenuItem("New Game ...");
				  newGame.addActionListener(this);
				  newGame.setActionCommand("newGame");
				  gameMenu.add(newGame);
				
				JMenuItem answerGame = new JMenuItem("Answer");
				  answerGame.addActionListener(this);
				  answerGame.setActionCommand("answerGame");
				  gameMenu.add(answerGame);
				  
				  JMenuItem exitGame = new JMenuItem("Exit");
				  exitGame.addActionListener(this);
				  exitGame.setActionCommand("exitGame");
				  gameMenu.add(exitGame);
				  
			}

			public void showLocation(int mouseX, int mouseY) //���콺 Ŀ������ġ�� ǥ��
			{
				if(mouseX!=this.mouseX) //���콺 Ŀ���� ��ġ�� ���� ���� ���
				{
					this.mouseX = mouseX;
					col.repaint();
				}
				if(mouseY!=this.mouseY) //���콺 Ŀ���� ��ġ�� ���� ���� ���
				{
					this.mouseY = mouseY;
					row.repaint();
				}
			}
			
			public void display() //������ Ǯ�ȴ��� ���θ� �˻�
			{
				boolean endFlag = true;
				for(int j=0; (j<10)&&endFlag; j++)
					for(int i=0; (i<10)&&endFlag; i++)
					{
						if((data.charAt(j*10+i)=='1')&&(temp[j*10+i]!=1))
							endFlag=false; //ä���� �� ĭ�� ���ä������ �˻�
						else if((data.charAt(j*10+i)!='1')&&(temp[j*10+i]==1))
							endFlag=false; //ä���� �ʾƾ� �� ĭ��ä������ �˻�
					}
				if(endFlag)
				{
					this.endFlag = endFlag;
					board.repaint(); //������ �� Ǯ������ ������ ĭ�� ä��
				}
			}
			
			public void actionPerformed(ActionEvent e) //������ �޴��� ��������� ��ƾ�� ȣ��
			{
				String cmd = e.getActionCommand();
				
				if(cmd.equals("newGame")) //�׸�׸���� �����͸� �ҷ��� �� ������ ����
					showOpenDialog();
				else if(cmd.equals("answerGame")) //Answer�� �����ϸ������� ���
				{
					this.endFlag = true;
					board.repaint();
				}
				else if(cmd.equals("exitGame")) //���� ����
					this.dispose();
			}
			
			//�޴����� New Game ���� ��, ���� �����͸� �ҷ����� �޼ҵ�
			public void showOpenDialog()
			{
				FileDialog fd = new FileDialog(this, "Open a File", FileDialog.LOAD);
				
				fd.setFile("*.nemo; *.NEMO"); //������ ������ Ȯ���ڴ�nemo �Ǵ� NEMO
				fd.setVisible(true);
				
				if(fd.getFile()!=null)
				{
					String filename = fd.getFile();
					String logicDir = fd.getDirectory();
					if(filename.indexOf('.')!=-1)
					{
						filename = (filename.substring(0, filename.indexOf('.'))).toLowerCase();
					}
					else
					{
						filename = filename.toLowerCase();
					}
					String logicName = filename;
					
					File f;
					FileInputStream from = null;
					BufferedReader d = null;
					
					try
					{
						f = new File(logicDir + logicName + ".nemo");
						from = new FileInputStream(f);
						d = new BufferedReader(new InputStreamReader(from));
						
						data = d.readLine();
						data.trim();
						
						d.close();
					}
					catch(IOException e)
					{
						System.out.println("I/O ERROR: "+ e);
					}
					
					//���� �ʱ�ȭ
					for(int i=0; i<100; i++)
					{
						temp[i] = 0;
					}
					this.endFlag = false;
					
					//�ҷ��� �����Ϳ� ���� column, row�� ���ڸ� ������ϰ� ������ ���带 �ٽ� ���
					col.getColumn();
					row.getRow();
					board.repaint();
				}
			}
}
