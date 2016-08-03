package com.projectles.povmt;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.projectles.povmt.activitys.RelatorioSemanalActivity;
import com.projectles.povmt.models.Atividade;
import com.projectles.povmt.models.TempoInvestido;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Danilo on 03/08/2016.
 */
public class HistoricoTest extends ApplicationTestCase<Application> {
    public HistoricoTest() {
        super(Application.class);
    }

    public void testTiMoreSevenDays(){
        boolean flagMoreSevenDays = false;

        RelatorioSemanalActivity relatorioSemanal = new RelatorioSemanalActivity();

        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.DATE, gc.get(Calendar.DATE) - 7);
        Date data = gc.getTime();

        List<Atividade> atividades = relatorioSemanal.getAtividades();
        for (int i = 0; i < atividades.size(); i++){
            Atividade atividade = atividades.get(i);
            List<TempoInvestido> listTis = atividade.getTis();
            for (int j = 0; j < listTis.size(); j++){
                if (listTis.get(j).getCriacao().before(data)){
                    flagMoreSevenDays = true;
                }

            }
        }

        assertFalse(flagMoreSevenDays);
    }
}
