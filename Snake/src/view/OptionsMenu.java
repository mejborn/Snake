package view;

import controller.Controller;
import model.Enumerators;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class OptionsMenu extends JPanel implements ActionListener {

	private final Controller controller;
	private final BufferedImage[] images;
	private final String[] tracks = {"16x9_noAI_empty.png", "16x9_straightLine.png", "32x18_withWalls.png", "48x27_noAI_withWormHoles.png",
			"48x27_withComplexWalls.png", "48x27_withWalls.png"};
	private String trackName;
	
	public OptionsMenu (Controller controller) {
		super();
		this.controller = controller;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		
		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(box);
		this.setBorder(new EmptyBorder((int) (screenHeight * 0.1), (int) (screenWidth * 0.3), (int) (screenHeight * 0.1), (int) (screenWidth * 0.3)));

		//Loading images of tracks
		images = new BufferedImage[tracks.length];
		Integer[] intTracks = new Integer[tracks.length];

		for (int i = 0; i < tracks.length; i++) {
			intTracks[i] = i;
			images[i] = createBufferedImage(tracks[i]);
//			if (images[i] != null) {
//				images[i].setDescription(new ImageIcon(tracks[i]));
//			}
		}

		//Add combobox
		JComboBox trackList = new JComboBox(intTracks);
		ComboBoxRenderer renderer = new ComboBoxRenderer(this);
		renderer.setPreferredSize(new Dimension(540, 180));
		trackList.setRenderer(renderer);
		trackList.setMaximumRowCount(3);
		trackList.addActionListener(this);
		

		JPanel trackPanel = new JPanel();
		trackPanel.add(trackList);

		this.add(trackPanel);
		
		JLabel speedLabel = new JLabel("Speed");
		speedLabel.setAlignmentX(CENTER_ALIGNMENT);
		speedLabel.setFont(new Font("Back", Font.PLAIN, 24));
		this.add(speedLabel);
		
		//slider with numbers for speed
		int majorTick = 45;
		int minorTick = 15;
		JSlider speedSlider = addSlider(majorTick, minorTick);
		
		speedSlider.setLabelTable(speedSlider.createStandardLabels(45));
		
		this.add(speedSlider, "Speed");
		
		this.add(Box.createRigidArea(new Dimension(0,50)));

		//Add Checkbox for AI
		JCheckBox enableAI = new JCheckBox("Enable AI");
		enableAI.setFont(new Font("Back", Font.PLAIN, 24));
		enableAI.setAlignmentX(CENTER_ALIGNMENT);
		enableAI.setPreferredSize(new Dimension(50, 50));
		enableAI.addActionListener(this);
		this.add(enableAI);

		this.add(Box.createRigidArea(new Dimension(0, 50)));
		
		JButton back = new JButton("Back");
		back.setFont(new Font("Back", Font.PLAIN, 24));
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.addActionListener(this);
		this.add(back);
	}

	private static BufferedImage createBufferedImage(String path) {
		BufferedImage images;
		try {
			System.out.println(path);
			images = ImageIO.read(new File(path));
			return images;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	JSlider addSlider(int major, int minor) {
		JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 10, 500, 100);
		slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    slider.setSnapToTicks(true);
	    slider.setMajorTickSpacing(major);
		slider.setMinorTickSpacing(minor);
		return slider;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch(e.getActionCommand()){
		case "Back":
			controller.doCmd(Enumerators.START_MENU);
			break;
			case "Enable AI":
				controller.doCmd(Enumerators.ENABLE_AI);
				break;
			case "comboBoxChanged":
				JComboBox trackList = (JComboBox) e.getSource();
				String trackName = trackList.getSelectedItem().toString();
				this.trackName = trackName;
				controller.doCmd(Enumerators.CHANGE_TRACK, trackName);
				System.out.println("did something");
				break;
		}
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public String[] getTracks() {
		return tracks;
	}
	
	public String getSelectedTrack() {
		return trackName;
	}
}
