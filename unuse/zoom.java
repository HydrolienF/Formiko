public class Zoom extends JFrame implements ActionListener {

	JPanel p;
	JButton buttonPlus, buttonMinus;
	double factor = 0.5;

	public void Foo(){ 
		super("ZOOM");

		p = new DrawingPanel();
		add(p, BorderLayout.CENTER);

		buttonMinus = new JButton(" - ");
		buttonPlus = new JButton(" + ");
		buttonMinus.addActionListener(this);
		buttonPlus.addActionListener(this);
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(buttonMinus);
		box.add(Box.createHorizontalStrut(20));
		box.add(buttonPlus);
		box.add(Box.createHorizontalGlue());
		add(box, BorderLayout.SOUTH);

		add(new JLabel("\n"), BorderLayout.NORTH);
		add(new JLabel("     "), BorderLayout.EAST);
		add(new JLabel("     "), BorderLayout.WEST);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600,400);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonPlus) {
			factor += 0.1;
		}
		else {
			factor -= 0.1;
		}
		buttonMinus.setEnabled(factor > 0.1);
		buttonPlus.setEnabled(factor < 4.0);
		p.repaint();
	}

	class DrawingPanel extends JPanel {

		double squareSize[] = {100.0, 50.0};
		DrawingPanel() {
			super(null);
			setBorder(BorderFactory.createLineBorder(Color.RED));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Dimension dim = this.getSize();
			int width = dim.width - 10;
			int height = dim.height - 10;
			for(int i = 0; i < squareSize.length; ++i) {
				int squareWidth = (int) (squareSize[i] * factor);
				int x = (width - squareWidth) / 2;
				int y = (height - squareWidth) / 2;
				g.drawRect(x, y, squareWidth, squareWidth);
			}
		}
	}
	public static void main(String[] args){
		new Zoom();
	}

}
