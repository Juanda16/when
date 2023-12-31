package com.when.holidays.services;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.when.holidays.entities.Holiday;
import com.when.holidays.repositories.IHolidaysRepository;

@Service
public class HolidaysService {

    @Autowired
    IHolidaysRepository repositorio;

    private Date obtenerDomingoPascua(int año) {
        int mes, dia, A, B, C, D, E, M, N;
        M = 0;
        N = 0;
        if (año >= 1583 && año <= 1699) {
            M = 22;
            N = 2;
        } else if (año >= 1700 && año <= 1799) {
            M = 23;
            N = 3;
        } else if (año >= 1800 && año <= 1899) {
            M = 23;
            N = 4;
        } else if (año >= 1900 && año <= 2099) {
            M = 24;
            N = 5;
        } else if (año >= 2100 && año <= 2199) {
            M = 24;
            N = 6;
        } else if (año >= 2200 && año <= 2299) {
            M = 25;
            N = 0;
        }

        A = año % 19;
        B = año % 4;
        C = año % 7;
        D = ((19 * A) + M) % 30;
        E = ((2 * B) + (4 * C) + (6 * D) + N) % 7;

        // Decidir entre los 2 casos
        if (D + E < 10) {
            dia = D + E + 22;
            mes = 3; // Marzo
        } else {
            dia = D + E - 9;
            mes = 4; // Abril
        }

        // Excepciones especiales
        if (dia == 26 && mes == 4)
            dia = 19;
        if (dia == 25 && mes == 4 && D == 28 && E == 6 && A > 10)
            dia = 18;
        return new Date(año - 1900, mes - 1, dia);
    }

    private Date agregarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, dias); // minus number would decrement the days
        return cal.getTime();
    }

    private Date siguienteLunes(Date fecha) {
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        if (c.get(Calendar.DAY_OF_WEEK) > Calendar.MONDAY)
            fecha = agregarDias(fecha, 9 - c.get(Calendar.DAY_OF_WEEK));
        else if (c.get(Calendar.DAY_OF_WEEK) < Calendar.MONDAY)
            fecha = agregarDias(fecha, 1);
        return fecha;
    }

    private List<Holiday> calcularFestivos(List<Holiday> festivos, int año) {
        if (festivos != null) {
            Date pascua = obtenerDomingoPascua(año);
            int i = 0;
            for (final Holiday festivo : festivos) {
                switch (festivo.getType().getId()) {
                    case 1:
                        festivo.setDate(new Date(año - 1900, festivo.getMonth() - 1, festivo.getDay()));
                        break;
                    case 2:
                        festivo.setDate(siguienteLunes(new Date(año - 1900, festivo.getMonth() - 1, festivo.getDay())));
                        break;
                    case 3:
                        festivo.setDate(agregarDias(pascua, festivo.getEasterDay()));
                        break;
                    case 4:
                        festivo.setDate(siguienteLunes(agregarDias(pascua, festivo.getEasterDay())));
                        break;
                }
                festivos.set(i, festivo);
                i++;
            }
        }
        return festivos;
    }

    public List<Holiday> obtenerFestivos(int año) {
        List<Holiday> festivos = repositorio.findAll();
        festivos = calcularFestivos(festivos, año);
        return festivos;
        // List<FestivoDto> fechas = new ArrayList<FestivoDto>();
        // for (final Holiday festivo : festivos) {
        // fechas.add(new FestivoDto(festivo.getNombre(), festivo.getDate()));
        // }
        // return fechas;
    }

    private boolean fechasIguales(Date fecha1, Date fecha2) {
        return fecha1.equals(fecha2);
    }

    private boolean esFestivo(List<Holiday> festivos, Date fecha) {
        if (festivos != null) {
            // if (festivos.get(0).getDate() != null && fecha.getYear() !=
            // festivos.get(0).getDate().getYear())
            festivos = calcularFestivos(festivos, fecha.getYear() + 1900);

            // System.out.println(fecha.getYear());

            for (final Holiday festivo : festivos) {
                if (fechasIguales(festivo.getDate(), fecha))
                    return true;
            }
        }
        return false;
    }

    public boolean esFestivo(Date fecha) {

        List<Holiday> festivos = repositorio.findAll();

        return esFestivo(festivos, fecha);
    }

    public boolean esFechaValida(String strFecha) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(strFecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
