package src.fichajes;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import net.sgoliver.android.toolbar3.Fragment2;
import net.sgoliver.android.toolbar3.R;

import java.sql.SQLException;

import src.BD.ConexionMySQL;
import src.dialogs.DialogoAlerta;
import src.dialogs.UtilesDialogos;
import src.fichajes.FichajeService;

/**
 * Created by Rober on 19/06/2016.
 */
public class ProcesoLecturaFichajes extends AsyncTask<FichajeService, Void, Void>{

    private FragmentManager fragmentManager;
    private FichajeService fichajeService;

    public ProcesoLecturaFichajes(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected Void doInBackground(FichajeService... parameters) {

        try {
            this.fichajeService = parameters[0];
            ConexionMySQL conexionMySQL = new ConexionMySQL();
            fichajeService.leeTodosFichajesProfeHoy(conexionMySQL);
            conexionMySQL.close();
        }
        catch (ClassNotFoundException | SQLException ex) {


            UtilesDialogos.muestraDialogo(this.fragmentManager, "Error al conectar con la BD. Contacte con el administrador.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Fragment2 fragment2 = (Fragment2) this.fragmentManager.findFragmentById(R.id.viewpager);
        fragment2.updateList(this.fichajeService.getFichajesProfe());
    }


}
