package app.sirius.spacecup.siriusapp.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.sirius.spacecup.siriusapp.R;
import app.sirius.spacecup.siriusapp.adapter.ListGrupoAdapter;
import app.sirius.spacecup.siriusapp.db.GrupoDAO;
import app.sirius.spacecup.siriusapp.db.LancamentoDAO;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCadPreLancamento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCadPreLancamento extends FragmentBase implements FragmentFooterBar.OnFragmentFooterBarInteractionListener {
    private static final String ARG_GRUPO = "grupo_id";
    private static final String ARG_LANCTO = "lancamento";
    private static final String ARG_READONLY = "readonly";

    private GrupoDAO.Grupo mGrupo;
    private LancamentoDAO.Lancamento mLancamento;
    private boolean mReadOnly;
    private EditText prelancto_angulo_lancto;
    private EditText prelancto_distanciaAlvo;
    private DatePicker prelancto_dtLancamento;
    private EditText prelancto_velocidade_vento;
    private EditText prelancto_peso_foguete;


    private EditText poslancto_pos_lancamento;
    private EditText poslancto_distancia_alcancada;
    private EditText poslancto_altura_max;
    private EditText poslancto_velocidade_max;
    private EditText poslancto_tmp_propulsao;
    private EditText poslancto_pico_acelaracao;
    private EditText poslancto_aceleracao_media;
    private EditText poslancto_tmp_apogeo_descida;
    private EditText poslancto_alt_ejecao;
    private EditText poslancto_taxa_descida;
    private EditText poslancto_duracao_voo;


    public FragmentCadPreLancamento() {
        // Required empty public constructor
    }

    public static FragmentCadPreLancamento newInstance() {
        return newInstance(null, false);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param grupo é um objeto <code>GrupoDAO.Grupo</code> grupo que vai abrir os dados de lancamento
     * @return A new instance of fragment FragmentCadPreLancamento.
     */
    public static FragmentCadPreLancamento newInstance(GrupoDAO.Grupo grupo, boolean openReadOnly) {
        FragmentCadPreLancamento fragment = new FragmentCadPreLancamento();
        Bundle args = newBundleArguments(grupo, openReadOnly);
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle newBundleArguments(GrupoDAO.Grupo grupo, boolean openReadOnly) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_GRUPO, grupo);
        args.putSerializable(ARG_READONLY, openReadOnly);
        return args;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_GRUPO, mGrupo);
        outState.putSerializable(ARG_LANCTO, mLancamento);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = null;
        if (savedInstanceState != null)
            args = savedInstanceState;
        if (getArguments() != null) {
            if (args != null)
                args.putAll(getArguments());
            else
                args = getArguments();
        }
        if (args != null) {
            if (args.containsKey(ARG_GRUPO)) {
                try {
                    mGrupo = (GrupoDAO.Grupo) args.getSerializable(ARG_GRUPO);
                } catch (ClassCastException e) {
                    throw new ClassCastException("parameter " + ARG_GRUPO
                            + " must be a GrupoDAO.Grupo class");
                }
            }
            if (args.containsKey(ARG_LANCTO)) {
                try {
                    mLancamento = (LancamentoDAO.Lancamento) args.getSerializable(ARG_LANCTO);
                } catch (ClassCastException e) {
                    throw new ClassCastException("parameter " + ARG_LANCTO
                            + " must be a LancamentoDAO.Lancamento class");
                }
            }
            mReadOnly = args.getBoolean(ARG_READONLY, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cad_pre_lancamento, container, false);

        prelancto_angulo_lancto = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        prelancto_distanciaAlvo = (EditText) view.findViewById(R.id.prelancto_distanciaAlvo);
        prelancto_dtLancamento = (DatePicker) view.findViewById(R.id.prelancto_dtLancamento);
        prelancto_velocidade_vento = (EditText) view.findViewById(R.id.prelancto_velocidade_vento);
        prelancto_peso_foguete = (EditText) view.findViewById(R.id.prelancto_peso_foguete);

        poslancto_distancia_alcancada = (EditText) view.findViewById(R.id.poslancto_distancia_alcancada);
        poslancto_altura_max = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_velocidade_max = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_tmp_propulsao = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_pico_acelaracao = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_aceleracao_media = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_tmp_apogeo_descida = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_alt_ejecao = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_taxa_descida = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);
        poslancto_duracao_voo = (EditText) view.findViewById(R.id.prelancto_angulo_lancto);

        Spinner prelancto_Grupos = (Spinner) view.findViewById(R.id.prelancto_Grupos);
        prelancto_Grupos.requestFocus();

        List<GrupoDAO.Grupo> listagrupos = new GrupoDAO(getContext()).doSelectAll();
        ListGrupoAdapter adap = new ListGrupoAdapter(getContext(), listagrupos);
        prelancto_Grupos.setAdapter(adap);
        prelancto_Grupos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGrupo = (GrupoDAO.Grupo) parent.getSelectedItem();
                FragmentBase.escondeTeclado(view, getContext());
                carregaDadosLancamento();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (mReadOnly) {
            View v = view.findViewById(R.id.footerbar_prelancto);
            if (v != null)
                v.setVisibility(View.GONE);
            prelancto_angulo_lancto.setEnabled(false);
            prelancto_distanciaAlvo.setEnabled(false);
            prelancto_dtLancamento.setEnabled(false);
            prelancto_velocidade_vento.setEnabled(false);
            prelancto_peso_foguete.setEnabled(false);
            if (mGrupo != null) {
                prelancto_Grupos.setSelection(listagrupos.indexOf(mGrupo), true);
                prelancto_Grupos.setEnabled(false);
            }
        }
        return view;
    }

    private void carregaDadosLancamento() {
        if (mGrupo == null) {
            Toast.makeText(getContext(), R.string.valida_grupos, Toast.LENGTH_LONG).show();
        } else {
            LancamentoDAO dao = new LancamentoDAO(getContext());
            mLancamento = dao.doSelectOne(mGrupo);
            prelancto_angulo_lancto.setText(String.valueOf(mLancamento.getAngulo_lancamento()));
            prelancto_distanciaAlvo.setText(String.valueOf(mLancamento.getDistancia_alvo()));
            prelancto_velocidade_vento.setText(String.valueOf(mLancamento.getVelocidade_vento()));
            prelancto_peso_foguete.setText(String.valueOf(mLancamento.getPeso_foguete()));

            poslancto_distancia_alcancada.setText(String.valueOf(mLancamento.getDistancia_alcancada()));
            poslancto_velocidade_max.setText(String.valueOf(mLancamento.getMaxima_velocidade()));
            poslancto_tmp_propulsao.setText(String.valueOf(mLancamento.getTempo_propoulsao()));
            poslancto_aceleracao_media.setText(String.valueOf(mLancamento.getAceleracao_media()));
            poslancto_tmp_apogeo_descida.setText(String.valueOf(mLancamento.getTempo_apogeo_distancia()));
            poslancto_alt_ejecao.setText(String.valueOf(mLancamento.getTempo_ejecao()));
            poslancto_taxa_descida.setText(String.valueOf(mLancamento.getTaxa_decida()));
            poslancto_duracao_voo.setText(String.valueOf(mLancamento.getDuracao_voo()));


            if (!mLancamento.getData().isEmpty()) {
                try {

                    // Isso da vontade de ... nos programadores que fazem voce usar formatador e ainda um Calendar pra manipular datas.
                    Date data = dao.dateFormat.parse(mLancamento.getData());
                    Calendar c = Calendar.getInstance();
                    c.setTime(data);

                    prelancto_dtLancamento.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    // setDate(df.parse(mLancamento.getData()).getTime());
                } catch (ParseException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onFragmentFooterBarSalvarClick(View view) {
        ProgressDialog pd = new ProgressDialog(getContext());
        try {
            pd.setIndeterminate(true);
            pd.setMessage(getContext().getString(R.string.aguarde));
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.show();

            LancamentoDAO dao = new LancamentoDAO(getContext());
            LancamentoDAO.Lancamento lan = dao.getObject();
            lan.setAngulo_lancamento(Double.parseDouble(String.valueOf(prelancto_angulo_lancto.getText())));
            lan.setAngulo_lancamento(Double.parseDouble(String.valueOf(prelancto_distanciaAlvo.getText())));
            String sData = dao.dateFormat.format(new Date(prelancto_dtLancamento.getYear(), prelancto_dtLancamento.getMonth(), prelancto_dtLancamento.getDayOfMonth()));
            lan.setData(sData);
            lan.setVelocidade_vento(Double.parseDouble(String.valueOf(prelancto_velocidade_vento.getText())));
            lan.setPeso_foguete(Double.parseDouble(String.valueOf(prelancto_peso_foguete.getText())));
            //POS
            if (lan.get_id() > 0) {
                lan.setDistancia_alcancada(Double.parseDouble(String.valueOf(poslancto_distancia_alcancada)));
                lan.setMaxima_velocidade(Double.parseDouble(String.valueOf(poslancto_velocidade_max)));
                lan.setTempo_propoulsao(Double.parseDouble(String.valueOf(poslancto_tmp_propulsao)));
                lan.setTempo_apogeo_distancia(Double.parseDouble(String.valueOf(poslancto_tmp_apogeo_descida)));
                lan.setTempo_ejecao(Double.parseDouble(String.valueOf(poslancto_alt_ejecao)));
                lan.setTaxa_decida(Double.parseDouble(String.valueOf(poslancto_taxa_descida)));
                lan.setDuracao_voo(Double.parseDouble(String.valueOf(poslancto_duracao_voo)));
            }
            lan.setGrupo_id(mGrupo.get_id());
            if (dao.doPersist()) {
                Toast.makeText(getContext(), "Dados de lançamento foram salvos com sucesso!", Toast.LENGTH_LONG).show();
            } else {
                Log.e(getClass().getSimpleName(), dao.getLastException().getMessage(), dao.getLastException());
                Toast.makeText(getContext(), "Ocorreu um erro ao salvar os dados de pré-lançamento!", Toast.LENGTH_LONG).show();
            }
        } finally {
            pd.dismiss();
            carregaDadosLancamento();
        }
    }
}
