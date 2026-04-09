import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaJogo {
	/* Layouts */
	private JFrame window;
	private Box principalLayout_V;
	private Box header;
	private Box body;
	private Box bodyLeft;
	private JPanel formAdivinha;
	private GridBagConstraints sizeControllerFormAdivinha;
	/* Widgets */
	private JButton iniciarButton;
	private JLabel acertosLabel;
	private JLabel penalidadeLabel;
	private JLabel dicaLabel;
	private JLabel letraLabel;
	private JTextField letraInput;
	private JButton adivinharButton;
	private JLabel palavraLabel;
	private JLabel systemMessageLabel;
	private JLabel imageGame;
	/* Game */
	private JogoDaForca game;


	public void main(String[] args) {
		createWindow();
		activate();
	}
	
	
	public void createWindow() {
		/* Layouts */
		this.window = new JFrame();
		this.principalLayout_V = new Box(BoxLayout.Y_AXIS);
		this.header = new Box(BoxLayout.X_AXIS);
		this.body = new Box(BoxLayout.X_AXIS);
		this.bodyLeft = new Box(BoxLayout.Y_AXIS);
		this.formAdivinha = new JPanel(new GridBagLayout());
		this.sizeControllerFormAdivinha = new GridBagConstraints();
		/* Widgets */
		this.iniciarButton = new JButton("");
		this.acertosLabel = new JLabel("");
		this.penalidadeLabel = new JLabel("");
		this.dicaLabel = new JLabel("");
		this.letraLabel = new JLabel("");
		this.letraInput = new JTextField();
		this.adivinharButton = new JButton("");
		this.palavraLabel = new JLabel("");
		this.systemMessageLabel = new JLabel("");
		this.imageGame = new JLabel(new ImageIcon("./imagens/6.png"));


		/* Application Window Config */

		this.window.setTitle("Joginho da Forca");
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setBounds(0, 0, 500, 300);
		this.window.add(principalLayout_V);
		
		
		/* Principal Layout Config */
		
		this.principalLayout_V.add(Box.createVerticalGlue());
		this.principalLayout_V.add(this.header);
		this.principalLayout_V.add(Box.createVerticalGlue());
		this.principalLayout_V.add(this.body);
		this.principalLayout_V.add(Box.createVerticalGlue());
		
		
		/* header Config */
		
		this.header.add(Box.createHorizontalGlue());
		this.header.add(this.iniciarButton);
		this.header.add(Box.createHorizontalGlue());
		this.header.add(this.acertosLabel);
		this.header.add(Box.createHorizontalGlue());
		this.header.add(this.penalidadeLabel);
		this.header.add(Box.createHorizontalGlue());


		/* Button "Iniciar" Config */

		this.iniciarButton.setText("Iniciar");
		this.iniciarButton.addActionListener(e -> {
			this.start();
		});
		

		/* Acertos Label Config */

		this.acertosLabel.setText("Acertos: ");
		this.acertosLabel.setForeground(Color.GREEN);
		this.acertosLabel.setVisible(false);

		/* Penalidades Label Config */

		this.penalidadeLabel.setText("Penalidades: ");
		this.penalidadeLabel.setForeground(Color.RED);
		this.penalidadeLabel.setVisible(false);


		/* Body Config */

		this.body.add(Box.createHorizontalGlue());
		this.body.add(this.bodyLeft);
		this.body.add(Box.createHorizontalGlue());
		this.body.add(this.imageGame);
		this.body.add(Box.createHorizontalGlue());


		/* Body Left Config */

		this.bodyLeft.add(Box.createVerticalGlue());
		this.bodyLeft.add(this.dicaLabel);
		this.bodyLeft.add(Box.createVerticalGlue());
		this.bodyLeft.add(this.formAdivinha);
		this.bodyLeft.add(Box.createVerticalGlue());
		this.bodyLeft.add(this.palavraLabel);
		this.bodyLeft.add(Box.createVerticalGlue());
		this.bodyLeft.add(this.systemMessageLabel);
		this.bodyLeft.add(Box.createVerticalGlue());
		
		
		/* Dica Label Config */

		this.dicaLabel.setText("Dica: ");
		this.dicaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);




		/* Size Controller Form Adivinha Config */
		
		this.sizeControllerFormAdivinha.weightx = 0.3;
		this.sizeControllerFormAdivinha.fill = GridBagConstraints.HORIZONTAL;
		
		
		/* Form Adivinha Config */

		this.formAdivinha.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.formAdivinha.getPreferredSize().height));
		this.formAdivinha.add(this.letraLabel);
		this.formAdivinha.add(this.letraInput, this.sizeControllerFormAdivinha);
		this.formAdivinha.add(this.adivinharButton);
		this.formAdivinha.setAlignmentX(Component.CENTER_ALIGNMENT);



		/* Letra Label Config */
		
		this.letraLabel.setText("Letra:");


		/* Letra Input Config */

		this.letraInput.setMaximumSize(new Dimension(this.letraInput.getPreferredSize().width, this.letraInput.getPreferredSize().height));
		this.letraInput.setEnabled(false);


		/* Adivinha Button Config */

		this.adivinharButton.setText("Adivinhar");
		this.adivinharButton.setEnabled(false);
		this.adivinharButton.addActionListener(e -> {
			guess();
		});


		/* Palavra Label Config */

		this.palavraLabel.setText("Palavra: ");
		this.palavraLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


		/* System Message Label */

		this.systemMessageLabel.setText("Bem Vindo");
		this.systemMessageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.systemMessageLabel.setVisible(false);



		/* Image Config */

		


		this.window.setVisible(true);
	}




	public void activate() {
		this.game = new JogoDaForca();
	}




	/* Events */

	public void start() {
		this.game.iniciar();
		startedHeader();
		startedBody();
		updateImage();
	}


	public void guess() {
		try {
			game.getOcorrencias(this.letraInput.getText());
			showSystemMessageLabel();

		} catch (Exception e) {
			this.systemMessageLabel.setText(e.getMessage());

		}

		showAcertosLabel();
		showPenalidadeLabel();
		showPalavraLabel();
		updateImage();

		this.letraInput.setText("");

		endingGame();
	}







	/* Header */

	public void startedHeader() {
		disableIniciarButton();
		showAcertosLabel();
		showPenalidadeLabel();
	}

	public void endingHeader() {
		enableIniciarButton();
		hideAcertosLabel();
		hidePenalidadeLabel();
	}
	
	
	public void enableIniciarButton() {
		this.iniciarButton.setEnabled(true);
	}

	public void disableIniciarButton() {
		this.iniciarButton.setEnabled(false);
	}

	public void showAcertosLabel() {
		this.acertosLabel.setText("Acertos: " + this.game.getAcertos());
		this.acertosLabel.setVisible(true);
	}

	public void hideAcertosLabel() {
		this.acertosLabel.setVisible(false);
	}

	public void showPenalidadeLabel() {
		this.penalidadeLabel.setText("Penalidade: " + this.game.getNomePenalidade());
		this.penalidadeLabel.setVisible(true);
	}

	public void hidePenalidadeLabel() {
		this.penalidadeLabel.setVisible(false);
	}




	/* Body */


	public void startedBody() {
		showDica();
		enableInputLetra();
		enableAdivinharButton();
		showPalavraLabel();
		showSystemMessageLabel();
	}

	public void endingBody() {
		hideDica();
		disableInputLetra();
		disableAdivinharButton();
		hidePalavraLabel();
		hideSystemMessageLabel();
	}



	public void showDica() {
		this.dicaLabel.setText("Dica: " + this.game.getDica());
	}

	public void hideDica() {
		this.dicaLabel.setText("Dica: ");
	}

	public void enableInputLetra() {
		this.letraInput.setEnabled(true);
	}

	public void disableInputLetra() {
		this.letraInput.setEnabled(false);
	}

	public void enableAdivinharButton() {
		this.adivinharButton.setEnabled(true);
	}

	public void disableAdivinharButton() {
		this.adivinharButton.setEnabled(false);
	}

	public void showPalavraLabel() {
		this.palavraLabel.setText("Palavra: " + game.getPalavra());
	}

	public void hidePalavraLabel() {
		this.palavraLabel.setText("Palavra:");
	}

	public void showSystemMessageLabel() {
		this.systemMessageLabel.setText(this.game.getResultado());
		this.systemMessageLabel.setVisible(true);
	}

	public void hideSystemMessageLabel() {
		this.systemMessageLabel.setText("");
		this.systemMessageLabel.setVisible(false);
	}

	public void updateSystemMessageLabel() {
		this.systemMessageLabel.setText(game.getResultados().getLast());
	}


	public void updateImage() {
		this.imageGame.setIcon(new ImageIcon("./imagens/" + this.game.getCodigoPenalidade() + ".png"));
	}



	public void endingGame() {
		if (!game.terminou()) return;

		updateSystemMessageLabel();
		disableInputLetra();
		disableAdivinharButton();
		enableIniciarButton();
	}
	
}
