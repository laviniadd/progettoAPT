package com.myproject.app.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.model.ListaSpesa;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AppSwingView extends JFrame implements AppViewInterface {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeLista;
//	private JList elencoListe;
	private JScrollPane scrollPane;
	private JButton btnCancellaListaSelezionata;
	private JButton btnModificaaggiungiProdotti;
	private JLabel lblErrorMessageListaLabel;
	private JLabel lblProdotto;
	private JTextField textField;
	private JLabel lblQuantit;
	private JSpinner spinner;
	private JButton btnAggiungiProdotto;
	private JList elencoProdotti;
	private JScrollPane scrollPane_1;
	private JButton btnCancellaProdottoSelezionato;
	private JButton btnModificaProdottoSelezionato;
	private JButton btnSalvaLista;
	private JButton btnCreaLista;
	private JLabel lblErrorMessageProdottoLabel;
	private JLabel lblErrorMessageProdottoEQuantitaLabel;
	private JList<ListaSpesa> elencoListe;
	private ListaSpesaController listaSpesaController;

	private DefaultListModel<ListaSpesa> elencoListeModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppSwingView frame = new AppSwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	DefaultListModel<ListaSpesa> getElencoListeModel() {
		return elencoListeModel;
	}

	public void setListaSpesaController(ListaSpesaController listaSpesaController) {
		this.listaSpesaController = listaSpesaController;
	}

	/**
	 * Create the frame.
	 */
	public AppSwingView() {
		setTitle("ShoppingListApp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 688, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNomeLista = new JLabel("Nome Lista:");
		GridBagConstraints gbc_lblNomeLista = new GridBagConstraints();
		gbc_lblNomeLista.insets = new Insets(0, 0, 5, 5);
		gbc_lblNomeLista.gridx = 0;
		gbc_lblNomeLista.gridy = 0;
		contentPane.add(lblNomeLista, gbc_lblNomeLista);

		txtNomeLista = new JTextField();
		KeyAdapter btnCreaListaEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnCreaLista.setEnabled(!txtNomeLista.getText().trim().isEmpty());
			}
		};
		txtNomeLista.addKeyListener(btnCreaListaEnabler);
		txtNomeLista.setName("nomeListaTextBox");
		GridBagConstraints gbc_txtNomeLista = new GridBagConstraints();
		gbc_txtNomeLista.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomeLista.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNomeLista.gridx = 1;
		gbc_txtNomeLista.gridy = 0;
		contentPane.add(txtNomeLista, gbc_txtNomeLista);
		txtNomeLista.setColumns(10);

		btnCreaLista = new JButton("Crea Lista");
		btnCreaLista.setEnabled(false);
		GridBagConstraints gbc_btnCreaLista = new GridBagConstraints();
		gbc_btnCreaLista.insets = new Insets(0, 0, 5, 0);
		gbc_btnCreaLista.gridwidth = 2;
		gbc_btnCreaLista.gridx = 0;
		gbc_btnCreaLista.gridy = 1;
		contentPane.add(btnCreaLista, gbc_btnCreaLista);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);

		elencoListeModel = new DefaultListModel<>();
		elencoListe = new JList<>(elencoListeModel);
		elencoListe.addListSelectionListener(
				e -> btnCancellaListaSelezionata.setEnabled(elencoListe.getSelectedIndex() != -1));

		elencoListe.setName("elencoListe");
		scrollPane.setViewportView(elencoListe);
		elencoListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btnCancellaListaSelezionata = new JButton("Cancella Lista selezionata");
		btnCancellaListaSelezionata.setEnabled(false);
		GridBagConstraints gbc_btnCancellaListaSelezionata = new GridBagConstraints();
		gbc_btnCancellaListaSelezionata.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancellaListaSelezionata.gridx = 0;
		gbc_btnCancellaListaSelezionata.gridy = 3;
		contentPane.add(btnCancellaListaSelezionata, gbc_btnCancellaListaSelezionata);

		btnModificaaggiungiProdotti = new JButton("Modifica/Aggiungi prodotti");
		btnModificaaggiungiProdotti.setEnabled(false);
		GridBagConstraints gbc_btnModificaaggiungiProdotti = new GridBagConstraints();
		gbc_btnModificaaggiungiProdotti.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificaaggiungiProdotti.gridx = 1;
		gbc_btnModificaaggiungiProdotti.gridy = 3;
		contentPane.add(btnModificaaggiungiProdotti, gbc_btnModificaaggiungiProdotti);

		lblErrorMessageListaLabel = new JLabel("");
		lblErrorMessageListaLabel.setName("errorMessageLabel");
		GridBagConstraints gbc_lblErrorMessageListaLabel = new GridBagConstraints();
		gbc_lblErrorMessageListaLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageListaLabel.gridwidth = 2;
		gbc_lblErrorMessageListaLabel.gridx = 0;
		gbc_lblErrorMessageListaLabel.gridy = 4;
		contentPane.add(lblErrorMessageListaLabel, gbc_lblErrorMessageListaLabel);

		lblProdotto = new JLabel("Prodotto:");
		GridBagConstraints gbc_lblProdotto = new GridBagConstraints();
		gbc_lblProdotto.insets = new Insets(0, 0, 5, 5);
		gbc_lblProdotto.gridx = 0;
		gbc_lblProdotto.gridy = 5;
		contentPane.add(lblProdotto, gbc_lblProdotto);

		textField = new JTextField();
		textField.setEnabled(false);
		textField.setName("prodottoTextBox");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);

		lblQuantit = new JLabel("Quantità:");
		GridBagConstraints gbc_lblQuantit = new GridBagConstraints();
		gbc_lblQuantit.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantit.gridx = 0;
		gbc_lblQuantit.gridy = 6;
		contentPane.add(lblQuantit, gbc_lblQuantit);

		spinner = new JSpinner();
		spinner.setName("quantita");
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setEnabled(false);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.anchor = GridBagConstraints.WEST;
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 6;
		contentPane.add(spinner, gbc_spinner);

		btnAggiungiProdotto = new JButton("Aggiungi Prodotto");
		btnAggiungiProdotto.setEnabled(false);
		GridBagConstraints gbc_btnAggiungiProdotto = new GridBagConstraints();
		gbc_btnAggiungiProdotto.insets = new Insets(0, 0, 5, 0);
		gbc_btnAggiungiProdotto.gridwidth = 2;
		gbc_btnAggiungiProdotto.gridx = 0;
		gbc_btnAggiungiProdotto.gridy = 7;
		contentPane.add(btnAggiungiProdotto, gbc_btnAggiungiProdotto);

		lblErrorMessageProdottoEQuantitaLabel = new JLabel("");
		GridBagConstraints gbc_lblErrorMessageProdottoEQuantitaLabel = new GridBagConstraints();
		gbc_lblErrorMessageProdottoEQuantitaLabel.gridwidth = 2;
		gbc_lblErrorMessageProdottoEQuantitaLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblErrorMessageProdottoEQuantitaLabel.gridx = 0;
		gbc_lblErrorMessageProdottoEQuantitaLabel.gridy = 8;
		contentPane.add(lblErrorMessageProdottoEQuantitaLabel, gbc_lblErrorMessageProdottoEQuantitaLabel);

		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 9;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		elencoProdotti = new JList();
		elencoProdotti.setName("elencoProdotti");
		elencoProdotti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(elencoProdotti);

		btnCancellaProdottoSelezionato = new JButton("Cancella Prodotto Selezionato");
		btnCancellaProdottoSelezionato.setEnabled(false);
		GridBagConstraints gbc_btnCancellaProdottoSelezionato = new GridBagConstraints();
		gbc_btnCancellaProdottoSelezionato.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancellaProdottoSelezionato.gridx = 0;
		gbc_btnCancellaProdottoSelezionato.gridy = 10;
		contentPane.add(btnCancellaProdottoSelezionato, gbc_btnCancellaProdottoSelezionato);

		btnModificaProdottoSelezionato = new JButton("Modifica Prodotto Selezionato");
		btnModificaProdottoSelezionato.setEnabled(false);
		GridBagConstraints gbc_btnModificaProdottoSelezionato = new GridBagConstraints();
		gbc_btnModificaProdottoSelezionato.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificaProdottoSelezionato.gridx = 1;
		gbc_btnModificaProdottoSelezionato.gridy = 10;
		contentPane.add(btnModificaProdottoSelezionato, gbc_btnModificaProdottoSelezionato);

		lblErrorMessageProdottoLabel = new JLabel("");
		GridBagConstraints gbc_lblErrorMessageProdottoLabel = new GridBagConstraints();
		gbc_lblErrorMessageProdottoLabel.gridwidth = 2;
		gbc_lblErrorMessageProdottoLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageProdottoLabel.gridx = 0;
		gbc_lblErrorMessageProdottoLabel.gridy = 11;
		contentPane.add(lblErrorMessageProdottoLabel, gbc_lblErrorMessageProdottoLabel);

		btnSalvaLista = new JButton("Salva Lista");
		btnSalvaLista.setEnabled(false);
		GridBagConstraints gbc_btnSalvaLista = new GridBagConstraints();
		gbc_btnSalvaLista.gridwidth = 2;
		gbc_btnSalvaLista.gridx = 0;
		gbc_btnSalvaLista.gridy = 12;
		contentPane.add(btnSalvaLista, gbc_btnSalvaLista);
	}

	@Override
	public void showAllEntity(List<ListaSpesa> entityDaMostrare) {
		entityDaMostrare.stream().forEach(elencoListeModel::addElement);

	}

	@Override
	public void showError(String message, ListaSpesa listaSpesaEntity) {
		lblErrorMessageListaLabel.setText(message + ": " + listaSpesaEntity);

	}

	@Override
	public void showNewEntity(ListaSpesa elencoEntity) {
		elencoListeModel.addElement(elencoEntity);
		resetErrorLabel();

	}

	@Override
	public void showRemovedEntity(ListaSpesa elencoEntityCancellate) {
		elencoListeModel.removeElement(elencoEntityCancellate);
		resetErrorLabel();

	}

	private void resetErrorLabel() {
		lblErrorMessageListaLabel.setText(" ");

	}
}
