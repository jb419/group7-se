import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * The GUI class is created and updated by the Simulation class.

 * 
 * @author Brett Flitter
 * @version 04/06/2012 - 3
 */
public class GUI
{
	private JLabel blackScoreLabel;
	private JLabel redScoreLabel;
	private JLabel roundLabel;
	private JTextField playerNameTextField;
	private JTextField worldLocationTextField;
	private JFrame frame;
	private JLabel[][] cells;
	private String lastPlayerAdded;
	private String lastWorldAdded;
	private Simulation simulation; 
	private int dimension;
	private JPanel centerPanel;
	
	/**
	 * Constructor
	 * @param simulation takes the simulation object has that the GUI can let the simulation know the user has performed an event
	 */
	public GUI(Simulation simulation) 
	{
		this.simulation = simulation;
		lastPlayerAdded = "";
		lastWorldAdded = "";

		build();
	}
	
	
	/**
	 * The build method creates everything you see! 
	 * The Center Panel used to hold the JLabels does not currently have a hexagon grid as this seems a little difficult
	 * to implement right now. Creating a grid that will adjust according to the size of the 2D array which holds the JLabels
	 * seems a little tricky as the hexagons need to be connected. There is more important things to think about first!
	 */
	public void build()
	{
		//make frame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		// TOP PANEL
		// make top panel to hold players scores and round number
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(BorderLayout.NORTH, topPanel);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		// make player1Score Label
		blackScoreLabel = new JLabel("player1Score", JLabel.CENTER);
		blackScoreLabel.setBorder(BorderFactory.createLineBorder(Color.black)); // let's see the label!  
		blackScoreLabel.setPreferredSize(new Dimension(250, 30)); 
		topPanel.add(blackScoreLabel);
		
		// make round Label
		roundLabel = new JLabel("round", JLabel.CENTER);
		roundLabel.setBorder(BorderFactory.createLineBorder(Color.black)); // let's see the label!  
        roundLabel.setPreferredSize(new Dimension(150, 30)); 
		topPanel.add(roundLabel);
		
		// make player2Score Label
		redScoreLabel = new JLabel("player2Score", JLabel.CENTER);
		redScoreLabel.setBorder(BorderFactory.createLineBorder(Color.black)); // let's see the label!  
		redScoreLabel.setPreferredSize(new Dimension(250, 30)); 
		topPanel.add(redScoreLabel);
		
		
		// BOTTON PANEL1
		// make bottom panel1
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 35, 10));
		bottomPanel.setPreferredSize(new Dimension(900, 100));
		frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);

		//make player names text field
		playerNameTextField = new JTextField();
		playerNameTextField.setText(" Enter player's name with their brain location");
		playerNameTextField.setPreferredSize(new Dimension(600, 30)); 
		playerNameTextField.addMouseListener(new TextFieldMouseListener());
		bottomPanel.add(playerNameTextField);
		
		//make add button
		JButton add = new JButton("   Add   ");
		add.addActionListener(new AddActionListener());
		bottomPanel.add(add);

		
		//make file location text field
		worldLocationTextField = new JTextField();
		worldLocationTextField.setText(" Enter world locations");
		worldLocationTextField.setPreferredSize(new Dimension(600, 30));
		worldLocationTextField.addMouseListener(new TextFieldMouseListener());
		bottomPanel.add(worldLocationTextField);
		
		//make start button
		JButton start = new JButton("  Start  ");
		start.addActionListener(new StartActionListener());
		bottomPanel.add(start);

		//CENTER PANEL
		//make center panel
		centerPanel = new JPanel();
		
	
		// set overall frame size
		frame.setSize(800,800);
		frame.setPreferredSize(new Dimension(800, 800));
		frame.setMinimumSize(new Dimension(800, 800));
		frame.setMaximumSize(new Dimension(800, 800));
		frame.setVisible(true);

	}
	
	/**
	 * Initialises the cells with the all world elements once the game is started.
	 * Note this is different from updateCell()
	 * 
	 * @param currentWorld a world is provided to fill the cells with it's elements
	 */
	public void loadCells(WorldToken[][] currentWorld)
	{
		
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(new FlowLayout(0,0,0));
		
		
		// CREATE JLABELS TO DISPLAY CONTENTS OF CELLS
		if (currentWorld.length == 150)
		{
			dimension = 3018;
		}
		else
		{
			// if using a 100 by 100 for testing
			dimension = 2018;
		}
		cells = new JLabel[currentWorld.length][currentWorld.length];
		
		centerPanel.setPreferredSize(new Dimension(dimension, dimension));
		centerPanel.setMinimumSize(new Dimension(dimension, dimension));
		centerPanel.setMaximumSize(new Dimension(dimension, dimension));

		
		boolean firstRowIn = false;
		int multiple = 0;
		
		for(int i = 0; i < currentWorld.length; i++)
		{
			for(int j = 0; j < currentWorld.length; j++)
			{
				String element ="";
				if (currentWorld[i][j].getType() == WorldTokenType.BlackAntHill)
				{
					element = "+";
				}
				else if (currentWorld[i][j].getType() == WorldTokenType.RedAntHill)
				{
					element = "-";
				}
				else if (currentWorld[i][j].getType() == WorldTokenType.Food)
				{
					element = "5";
				}
				else if (currentWorld[i][j].getType() == WorldTokenType.Empty)
				{
					element = ".";
				}
				else
				{
					element = "#";
				}
				JLabel cell = new JLabel(element, JLabel.CENTER);
				cell.setPreferredSize(new Dimension(20, 20));
				
				cells[i][j] = cell;
				centerPanel.add(cells[i][j]);
				if(i == 0 && j == currentWorld.length-1 && firstRowIn == false)
				{
					// blank cells need to create the indentations to give the hexagon grid look
					// the first row needs 2 blanks placed after the last label is placed
					centerPanel.add(createBlankCell());
					centerPanel.add(createBlankCell());
					firstRowIn = true;
				}
				else if (firstRowIn)
				{
					multiple++;
					// blank cells are only needed after every other row (after the first row has been placed)
					if (multiple % (currentWorld.length * 2) == 0)
					{
						centerPanel.add(createBlankCell());
						centerPanel.add(createBlankCell());
					}
				}
				
			}
		}
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		
		//make scroll pane which holds the center panel and provides scroll bars
				int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
			    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS; 
			    JScrollPane jsp = new JScrollPane(centerPanel,v,h); 
				frame.getContentPane().add(jsp);
		
				// set overall frame size
				frame.setSize(800,800);
				frame.setPreferredSize(new Dimension(800, 800));
				frame.setMinimumSize(new Dimension(800, 800));
				frame.setMaximumSize(new Dimension(800, 800));
				frame.setVisible(true);
	}
	
	/**
	 * Update cell
	 * 
	 * @param row the row of the 2D array of JLabels
	 * @param col the col of the 2D array of JLabels
	 * @param value the value to be placed in cell
	 */
	public void updateCell(int row, int col, String value)
	{
		cells[row][col].setText(value);
	}
	
	private JLabel createBlankCell()
	{
		// creates a blank cell
		JLabel blankCell = new JLabel(" ", JLabel.CENTER);
		blankCell.setPreferredSize(new Dimension(10, 20));
		
		return blankCell;
		
	}
	
	/**
	 * The updateScore() will be used by the Simulator class to update the player's score
	 * 
	 * @param antColour the colour of player to update
	 * @param players name
	 * @param value the score to be updated
	 */
	public void updateScore(String antColour, String name, String value)
	{
		if (antColour.equals("black"))
		{
			blackScoreLabel.setText("" + name + " score: " + value);
		}
		else 
		{
			redScoreLabel.setText("" + name + " score: " + value);
		}
			
	}
	
	/**
	 * Out put a warning message when there is an error
	 * 
	 * @param message the message which details what error was caused.
	 */
	public void outPutError(String message)
	{
		if (message.equals("brain"))
		{
			playerNameTextField.setText(lastPlayerAdded); // so that the user can edit their mistake instead of having to re-type the whole thing
			JOptionPane.showMessageDialog(frame, "Player text field input is not syntactically correct!", "Input incorrect", JOptionPane.ERROR_MESSAGE);
		}
		else if (message.equals("world"))
		{
			worldLocationTextField.setText(lastWorldAdded); // so that the user can edit their mistake instead of having to re-type the whole thing
			JOptionPane.showMessageDialog(frame, "World text field input is not syntactically correct!", "Input incorrect", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Out puts the winner as message Dialog
	 * 
	 * @param victor the winner of the tournament
	 */
	public void showVictor(String victor)
	{
		JOptionPane.showMessageDialog(frame, "The winner is " + victor, "Winner", JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/**
	 * StartActionListener inner class is used to listen for the start button to be clicked
	 */
	class StartActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(simulation.getNumOfPlayers()>= 2)
			{
				// set text fields so to un-editable
				playerNameTextField.setEditable(false); 
				worldLocationTextField.setEditable(false);
			
				// WAKEY WAKEY SIMULATOR TIME TO START
			
				simulation.run();
			}
			else
			{
				outPutError("Not enough players!");
			}
		}
	}
	
	
	/**
	 * AddActionListener inner class is used to listen for the start button to be clicked
	 */
	class AddActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			// make sure text field is not empty or previous text remains before adding
			if (!playerNameTextField.getText().equals("") && !playerNameTextField.getText().equals(lastPlayerAdded))
			{
				lastPlayerAdded = playerNameTextField.getText();
				simulation.addPlayerAndBrain(playerNameTextField.getText());
				playerNameTextField.setText("");
			}
			// make sure text field is not empty or previous text remains before adding
			if  (!worldLocationTextField.getText().equals("")  && !worldLocationTextField.getText().equals(lastWorldAdded))
			{
				lastWorldAdded = worldLocationTextField.getText();
				simulation.addWorldLocation(worldLocationTextField.getText());
				worldLocationTextField.setText("");
			}
			
			
		}
	}
	
	/**
	 * TextFieldMouseListener inner class is used to listen for either of the text field to be clicked.
	 * The current text will be cleared so that the user can input their text
	 */
	class TextFieldMouseListener implements MouseListener
	{
		// when the either of the text fields are clicked the follow occurs..
		@Override
		public void mouseClicked(MouseEvent event)
		{
			//When user clicks in text field for the first time, the text field is cleared of it's current text
			//allowing the user to type their names, brain location and world location etc.
			if (event.getSource() == playerNameTextField && playerNameTextField.getText().contains("Enter"))
			{
				playerNameTextField.setText("");
			}
			if (event.getSource() == worldLocationTextField && worldLocationTextField.getText().contains("Enter"))
			{
				worldLocationTextField.setText("");
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
			
		}

	}
}
