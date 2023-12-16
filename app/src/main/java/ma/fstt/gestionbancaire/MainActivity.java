package ma.fstt.gestionbancaire;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner compteDropdown;
    private Spinner destinataireDropdown;
    private RadioButton debitRadioButton;
    private RadioButton creditRadioButton;
    private RadioButton virementRadioButton;
    private Button validerButton;
    private TextView montantEditText;
    private TextView soldeTextView;

    private List<Compte> comptes;
    private Compte selectedCompte;
    private Compte selectedDestinataire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soldeTextView = findViewById(R.id.textView4);
        compteDropdown = findViewById(R.id.spinner);
        destinataireDropdown = findViewById(R.id.spinnerDestinataire);
        montantEditText = findViewById(R.id.montant);
        debitRadioButton = findViewById(R.id.radioButton);
        creditRadioButton = findViewById(R.id.radioButton2);
        virementRadioButton = findViewById(R.id.radioButtonVirement);
        validerButton = findViewById(R.id.button);

        // Initialize list of Comptes
        comptes = new ArrayList<>();
        Compte compte1 = new Compte();
        compte1.setNumCompte(1L);
        compte1.setNom("Account 1");
        compte1.setSolde(5000.0);

        Compte compte2 = new Compte();
        compte2.setNumCompte(2L);
        compte2.setNom("Account 2");
        compte2.setSolde(10000.0);

        Compte compte3 = new Compte();
        compte3.setNumCompte(13L);
        compte3.setNom("Account 3");
        compte3.setSolde(9000.0);

        Compte compte4 = new Compte();
        compte4.setNumCompte(712L);
        compte4.setNom("Account 4");
        compte4.setSolde(14030.0);

        // Add created Compte instances to the list
        comptes.add(compte1);
        comptes.add(compte2);
        comptes.add(compte3);
        comptes.add(compte4);

        ArrayAdapter<Compte> compteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comptes);
        compteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compteDropdown.setAdapter(compteAdapter);

        ArrayAdapter<Compte> destinataireAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, comptes);
        destinataireAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destinataireDropdown.setAdapter(destinataireAdapter);


        // Hide the destinataire layout initially
        findViewById(R.id.desstinataire).setVisibility(View.GONE);

        virementRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (virementRadioButton.isChecked()) {
                    findViewById(R.id.desstinataire).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.desstinataire).setVisibility(View.GONE);
                    selectedDestinataire = null; // Reset selectedDestinataire when "Virement" is deselected
                }
                updateValiderButtonState(); // Update button state when visibility changes
            }
        });


        compteDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCompte = (Compte) parentView.getItemAtPosition(position);
                // Update soldeTextView with the selected Compte's solde
                if (selectedCompte != null) {
                    soldeTextView.setText(String.valueOf(selectedCompte.getSolde()));
                    updateValiderButtonState();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // event when nothing is selected
            }
        });

        destinataireDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDestinataire = (Compte) parentView.getItemAtPosition(position);
                updateValiderButtonState();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // event when nothing is selected
            }
        });

        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int montant = Integer.parseInt(montantEditText.getText().toString());
                if (virementRadioButton.isChecked()) {
                    if (montant <= selectedCompte.getSolde()) {
                        selectedCompte.setSolde(selectedCompte.getSolde() - montant);
                        selectedDestinataire.setSolde(selectedDestinataire.getSolde() + montant);
                        soldeTextView.setText(String.valueOf(selectedCompte.getSolde()));
                    }
                } else if (debitRadioButton.isChecked()) {
                    if (montant <= selectedCompte.getSolde()) {
                        selectedCompte.setSolde(selectedCompte.getSolde() - montant);
                        soldeTextView.setText(String.valueOf(selectedCompte.getSolde()));
                    }
                } else if (creditRadioButton.isChecked()) {
                    selectedCompte.setSolde(selectedCompte.getSolde() + montant);
                    soldeTextView.setText(String.valueOf(selectedCompte.getSolde()));
                }
                updateValiderButtonState();
            }
        });

        debitRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.desstinataire).setVisibility(View.GONE);
                selectedDestinataire = null;
                updateValiderButtonState();
            }
        });

        creditRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.desstinataire).setVisibility(View.GONE);
                selectedDestinataire = null;
                updateValiderButtonState();
            }
        });

        updateValiderButtonState();
    }

    private void updateValiderButtonState() {
        int montant = 0;
        if (!montantEditText.getText().toString().isEmpty()) {
            montant = Integer.parseInt(montantEditText.getText().toString());
        }

        if (selectedCompte != null && selectedDestinataire != null) {
            if (montant > selectedCompte.getSolde()) {
                validerButton.setEnabled(false);
            } else {
                validerButton.setEnabled(true);
            }
        }
    }
}
