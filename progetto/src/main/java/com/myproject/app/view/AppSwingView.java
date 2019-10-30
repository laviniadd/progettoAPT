package com.myproject.app.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.controller.ProdottoController;
import com.myproject.app.model.ListaSpesa;
import com.myproject.app.model.Prodotto;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AppSwingView extends JFrame implements AppViewInterface {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeLista;
	private JScrollPane scrollPane;
	private JButton btnCancellaListaSelezionata;
	private JButton btnModificaAggiungiProdotti;
	private JLabel lblErrorMessageListaLabel;
	private JLabel lblProdotto;
	private JTextField textProdotto;
	private JTextField textQuantita;
	private JLabel lblQuantita;
	private JButton btnAggiungiProdotto;
	private JScrollPane scrollPane_1;
	private JButton btnCancellaProdottoSelezionato;
	private JButton btnModificaProdottoSelezionato;
	private JButton btnSalvaProdottoModificato;
	private JButton btnCreaLista;
	private JLabel lblErrorMessageProdottoLabel;
	private JLabel lblErrorMessageProdottoEQuantitaLabel;
	private transient ListaSpesa listaDaAssociareAiProdotti;
	private JList<Prodotto> listaProdotti;
	private JList<ListaSpesa> listaListe;
	private DefaultListModel<Prodotto> listaProdottiModel;
	private DefaultListModel<ListaSpesa> listaListeSpesaModel;
	private boolean modifierMode = false;
	private transient ListaSpesaController listaSpesaController;
	private transient ProdottoController prodottoController;

	public DefaultListModel<ListaSpesa> getListaListeSpesaModel() {
		return listaListeSpesaModel;
	}

	public DefaultListModel<Prodotto> getListaProdottiModel() {
		return listaProdottiModel;
	}

	public void setViewController(ListaSpesaController listaSpesaController, ProdottoController prodottoController) {
		this.listaSpesaController = listaSpesaController;
		this.prodottoController = prodottoController;
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
		gbc_lblNomeLista.anchor = GridBagConstraints.EAST;
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
		btnCreaLista.addActionListener(e -> {
			listaDaAssociareAiProdotti = new ListaSpesa();
			listaSpesaController.saveNewLista(new ListaSpesa(txtNomeLista.getText()));
			txtNomeLista.setText("");
			btnCreaLista.setEnabled(false);
			textProdotto.setEditable(false);
			textQuantita.setEditable(false);
		});
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

		listaListeSpesaModel = new DefaultListModel<>();
		listaListe = new JList<>(listaListeSpesaModel);
		listaListe.addListSelectionListener(e -> {
			if (listaListe.getSelectedIndex() != -1) {
				listaProdottiModel.clear();
				btnSalvaProdottoModificato.setEnabled(false);
				btnAggiungiProdotto.setEnabled(false);
				btnCancellaProdottoSelezionato.setEnabled(false);
				btnModificaProdottoSelezionato.setEnabled(false);
				textProdotto.setEditable(false);
				textQuantita.setEditable(false);
			}
			btnCancellaListaSelezionata.setEnabled(listaListe.getSelectedIndex() != -1);
			btnModificaAggiungiProdotti.setEnabled(listaListe.getSelectedIndex() != -1);
		});
		listaListe.setName("elencoListe");
		scrollPane.setViewportView(listaListe);
		listaListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		btnCancellaListaSelezionata = new JButton("Cancella Lista selezionata");
		btnCancellaListaSelezionata
				.addActionListener(e -> listaSpesaController.deleteListaSpesa(listaListe.getSelectedValue()));
		btnCancellaListaSelezionata.setEnabled(false);
		GridBagConstraints gbc_btnCancellaListaSelezionata = new GridBagConstraints();
		gbc_btnCancellaListaSelezionata.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancellaListaSelezionata.gridx = 0;
		gbc_btnCancellaListaSelezionata.gridy = 3;
		contentPane.add(btnCancellaListaSelezionata, gbc_btnCancellaListaSelezionata);

		btnModificaAggiungiProdotti = new JButton("Modifica/Aggiungi prodotti");
		btnModificaAggiungiProdotti.addActionListener(e -> {
			textProdotto.setEditable(true);
			textQuantita.setEditable(true);
			listaDaAssociareAiProdotti = listaListe.getSelectedValue();
			prodottoController.allProductsGivenAList(listaDaAssociareAiProdotti);
			btnCancellaListaSelezionata.setEnabled(false);
			btnModificaAggiungiProdotti.setEnabled(false);
		});
		btnModificaAggiungiProdotti.setEnabled(false);
		GridBagConstraints gbc_btnModificaaggiungiProdotti = new GridBagConstraints();
		gbc_btnModificaaggiungiProdotti.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificaaggiungiProdotti.gridx = 1;
		gbc_btnModificaaggiungiProdotti.gridy = 3;
		contentPane.add(btnModificaAggiungiProdotti, gbc_btnModificaaggiungiProdotti);

		lblErrorMessageListaLabel = new JLabel("");
		lblErrorMessageListaLabel.setName("errorMessageLabelList");
		GridBagConstraints gbc_lblErrorMessageListaLabel = new GridBagConstraints();
		gbc_lblErrorMessageListaLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageListaLabel.gridwidth = 2;
		gbc_lblErrorMessageListaLabel.gridx = 0;
		gbc_lblErrorMessageListaLabel.gridy = 4;
		contentPane.add(lblErrorMessageListaLabel, gbc_lblErrorMessageListaLabel);

		lblProdotto = new JLabel("Prodotto:");
		GridBagConstraints gbc_lblProdotto = new GridBagConstraints();
		gbc_lblProdotto.anchor = GridBagConstraints.EAST;
		gbc_lblProdotto.insets = new Insets(0, 0, 5, 5);
		gbc_lblProdotto.gridx = 0;
		gbc_lblProdotto.gridy = 5;
		contentPane.add(lblProdotto, gbc_lblProdotto);

		textProdotto = new JTextField();
		textProdotto.setEditable(false);
		KeyAdapter btnAggiungiProdottoEnabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					if (!modifierMode) {
						btnAggiungiProdotto.setEnabled(!textProdotto.getText().trim().isEmpty()
								&& Integer.parseInt(textQuantita.getText().trim()) >= 1);
					} else {
						btnSalvaProdottoModificato.setEnabled(!textProdotto.getText().trim().isEmpty()
								&& Integer.parseInt(textQuantita.getText().trim()) >= 1);
					}
				} catch (NumberFormatException nfe) {
					btnAggiungiProdotto.setEnabled(false);
				}
			}
		};
		textProdotto.addKeyListener(btnAggiungiProdottoEnabler);
		textProdotto.setName("prodottoTextBox");
		GridBagConstraints gbc_textProdotto = new GridBagConstraints();
		gbc_textProdotto.insets = new Insets(0, 0, 5, 0);
		gbc_textProdotto.fill = GridBagConstraints.HORIZONTAL;
		gbc_textProdotto.gridx = 1;
		gbc_textProdotto.gridy = 5;
		contentPane.add(textProdotto, gbc_textProdotto);
		textProdotto.setColumns(10);

		lblQuantita = new JLabel("Quantità:");
		GridBagConstraints gbc_lblQuantit = new GridBagConstraints();
		gbc_lblQuantit.anchor = GridBagConstraints.EAST;
		gbc_lblQuantit.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantit.gridx = 0;
		gbc_lblQuantit.gridy = 6;
		contentPane.add(lblQuantita, gbc_lblQuantit);

		textQuantita = new JTextField();
		textQuantita.setEditable(false);
		textQuantita.setText("1");
		textQuantita.addKeyListener(btnAggiungiProdottoEnabler);
		textQuantita.setName("quantitaTextBox");
		GridBagConstraints gbc_textQuantita = new GridBagConstraints();
		gbc_textQuantita.insets = new Insets(0, 0, 5, 0);
		gbc_textQuantita.fill = GridBagConstraints.HORIZONTAL;
		gbc_textQuantita.gridx = 1;
		gbc_textQuantita.gridy = 6;
		contentPane.add(textQuantita, gbc_textQuantita);
		textQuantita.setColumns(10);

		btnAggiungiProdotto = new JButton("Aggiungi Prodotto");
		btnAggiungiProdotto.addActionListener(e -> {
			Prodotto prodottoDaAggiungere = new Prodotto(textProdotto.getText().trim(),
					Integer.parseInt(textQuantita.getText().trim()), listaDaAssociareAiProdotti);
			prodottoController.saveNewProduct(prodottoDaAggiungere);
			btnAggiungiProdotto.setEnabled(false);
			textProdotto.setText("");
			textQuantita.setText("");
			textQuantita.setText("1");
		});

		btnSalvaProdottoModificato = new JButton("Salva Prodotto Modificato");
		btnSalvaProdottoModificato.addActionListener(e -> {
			modifierMode = false;
			prodottoController.updateProduct(listaProdotti.getSelectedValue(), textProdotto.getText().trim(),
					Integer.parseInt(textQuantita.getText().trim()));
			listaProdottiModel.removeElement(listaProdotti.getSelectedValue());
			listaProdotti.setEnabled(true);
			textProdotto.setText("");
			textQuantita.setText("");
			textQuantita.setText("1");
			btnSalvaProdottoModificato.setEnabled(false);
		});
		btnSalvaProdottoModificato.setEnabled(false);
		GridBagConstraints gbc_btnSalvaProdottoModificato = new GridBagConstraints();
		gbc_btnSalvaProdottoModificato.insets = new Insets(0, 0, 5, 5);
		gbc_btnSalvaProdottoModificato.gridx = 0;
		gbc_btnSalvaProdottoModificato.gridy = 7;
		contentPane.add(btnSalvaProdottoModificato, gbc_btnSalvaProdottoModificato);
		btnAggiungiProdotto.setEnabled(false);
		GridBagConstraints gbc_btnAggiungiProdotto = new GridBagConstraints();
		gbc_btnAggiungiProdotto.insets = new Insets(0, 0, 5, 0);
		gbc_btnAggiungiProdotto.gridx = 1;
		gbc_btnAggiungiProdotto.gridy = 7;
		contentPane.add(btnAggiungiProdotto, gbc_btnAggiungiProdotto);

		lblErrorMessageProdottoEQuantitaLabel = new JLabel("");
		lblErrorMessageProdottoEQuantitaLabel.setName("errorMessageProductModifiedLabel");
		GridBagConstraints gbc_lblErrorMessageProdottoEQuantitaLabel = new GridBagConstraints();
		gbc_lblErrorMessageProdottoEQuantitaLabel.gridwidth = 2;
		gbc_lblErrorMessageProdottoEQuantitaLabel.insets = new Insets(0, 0, 5, 0);
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

		listaProdottiModel = new DefaultListModel<>();
		listaProdotti = new JList<>(listaProdottiModel);
		listaProdotti.addListSelectionListener(e -> {

			btnCancellaProdottoSelezionato.setEnabled(listaProdotti.getSelectedIndex() != -1);
			btnModificaProdottoSelezionato.setEnabled(listaProdotti.getSelectedIndex() != -1);

		});
		listaProdotti.setName("elencoProdotti");
		listaProdotti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(listaProdotti);

		btnCancellaProdottoSelezionato = new JButton("Cancella Prodotto Selezionato");
		btnCancellaProdottoSelezionato.addActionListener(e -> {
			prodottoController.deleteProduct(listaProdotti.getSelectedValue());
			btnCancellaProdottoSelezionato.setEnabled(false);
			btnModificaProdottoSelezionato.setEnabled(false);
		});
		btnCancellaProdottoSelezionato.setEnabled(false);
		GridBagConstraints gbc_btnCancellaProdottoSelezionato = new GridBagConstraints();
		gbc_btnCancellaProdottoSelezionato.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancellaProdottoSelezionato.gridx = 0;
		gbc_btnCancellaProdottoSelezionato.gridy = 10;
		contentPane.add(btnCancellaProdottoSelezionato, gbc_btnCancellaProdottoSelezionato);

		btnModificaProdottoSelezionato = new JButton("Modifica Prodotto Selezionato");
		btnModificaProdottoSelezionato.addActionListener(e -> {
			btnSalvaProdottoModificato.setEnabled(true);
			modifierMode = true;
			btnCancellaProdottoSelezionato.setEnabled(false);
			Prodotto prodotto = listaProdotti.getSelectedValue();
			textProdotto.setEditable(true);
			textQuantita.setEditable(true);
			textProdotto.setText(prodotto.getName());
			textQuantita.setText(String.valueOf(prodotto.getQuantity()));
			btnModificaProdottoSelezionato.setEnabled(false);
			listaProdotti.setEnabled(false);
		});
		btnModificaProdottoSelezionato.setEnabled(false);
		GridBagConstraints gbc_btnModificaProdottoSelezionato = new GridBagConstraints();
		gbc_btnModificaProdottoSelezionato.insets = new Insets(0, 0, 5, 0);
		gbc_btnModificaProdottoSelezionato.gridx = 1;
		gbc_btnModificaProdottoSelezionato.gridy = 10;
		contentPane.add(btnModificaProdottoSelezionato, gbc_btnModificaProdottoSelezionato);

		lblErrorMessageProdottoLabel = new JLabel("");
		lblErrorMessageProdottoLabel.setName("errorMessageProductLabel");
		GridBagConstraints gbc_lblErrorMessageProdottoLabel = new GridBagConstraints();
		gbc_lblErrorMessageProdottoLabel.gridwidth = 2;
		gbc_lblErrorMessageProdottoLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblErrorMessageProdottoLabel.gridx = 0;
		gbc_lblErrorMessageProdottoLabel.gridy = 11;
		contentPane.add(lblErrorMessageProdottoLabel, gbc_lblErrorMessageProdottoLabel);
	}

	@Override
	public <T> void showAllEntities(List<T> entitiesDaMostrare) {
		if (entitiesDaMostrare.isEmpty()) {
			return;
		}
		if (entitiesDaMostrare.get(0).getClass().equals(ListaSpesa.class)) {
			SwingUtilities.invokeLater(() -> {
				for (int i = 0; i < entitiesDaMostrare.size(); i++) {
					listaListeSpesaModel.addElement((ListaSpesa) entitiesDaMostrare.get(i));
				}
			});
		}
		if (entitiesDaMostrare.get(0).getClass().equals(Prodotto.class)) {
			SwingUtilities.invokeLater(() -> {
				listaProdottiModel.clear();
				for (int i = 0; i < entitiesDaMostrare.size(); i++) {
					listaProdottiModel.addElement((Prodotto) entitiesDaMostrare.get(i));
				}
			});
		}
	}

	@Override
	public <T> void showNewEntity(T entity) {
		if (entity.getClass().equals(ListaSpesa.class)) {
			SwingUtilities.invokeLater(() -> {
				listaListeSpesaModel.addElement((ListaSpesa) entity);
				resetErrorLabel();
			});

		}
		if (entity.getClass().equals(Prodotto.class)) {
			SwingUtilities.invokeLater(() -> {
				listaProdottiModel.addElement((Prodotto) entity);
				resetErrorLabel();
			});
		}
	}

	@Override
	public <T> void showRemovedEntity(T entityCancellate) {

		if (entityCancellate.getClass().equals(ListaSpesa.class)) {
			SwingUtilities.invokeLater(() -> {
				listaListeSpesaModel.removeElement(entityCancellate);
				resetErrorLabel();
			});
		}
		if (entityCancellate.getClass().equals(Prodotto.class)) {
			SwingUtilities.invokeLater(() -> {
				listaProdottiModel.removeElement(entityCancellate);
				resetErrorLabel();
			});
		}
	}

	@Override
	public <T> void showError(String errorMessage, T entity) {
		if (errorMessage.equals("This shopping list already exist")) {
			SwingUtilities.invokeLater(() -> lblErrorMessageListaLabel.setText(errorMessage + ": " + entity));
		}
		if (errorMessage.equals("This product already exist")) {
			SwingUtilities
					.invokeLater(() -> lblErrorMessageProdottoEQuantitaLabel.setText(errorMessage + ": " + entity));
		}
		if (errorMessage.equals("This product has no valid name or quantity values")) {
			SwingUtilities
					.invokeLater(() -> lblErrorMessageProdottoEQuantitaLabel.setText(errorMessage + ": " + entity));
		}
	}

	private void resetErrorLabel() {
		lblErrorMessageListaLabel.setText(" ");
		lblErrorMessageProdottoEQuantitaLabel.setText(" ");
		lblErrorMessageProdottoLabel.setText(" ");
	}

	@Override
	public <T> void showErrorEntityNotFound(String errorMessage, T entity) {
		if (errorMessage.equals("This product does not exist")) {
			SwingUtilities.invokeLater(() -> {
				lblErrorMessageProdottoLabel.setText(errorMessage + ": " + entity);
				lblErrorMessageProdottoEQuantitaLabel.setText(errorMessage + ": " + entity);
				listaProdottiModel.removeElement(entity);
			});
		}
		if (errorMessage.equals("This shopping list does not exist")) {
			SwingUtilities.invokeLater(() -> {
				lblErrorMessageListaLabel.setText(errorMessage + ": " + entity);
				listaListeSpesaModel.removeElement(entity);
				textProdotto.setEditable(false);
				textQuantita.setEditable(false);
				btnModificaAggiungiProdotti.setEnabled(false);
				btnAggiungiProdotto.setEnabled(false);
			});
		}
	}
}
