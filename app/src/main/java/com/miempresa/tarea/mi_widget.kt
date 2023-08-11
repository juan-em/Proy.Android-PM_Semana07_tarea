package com.miempresa.tarea

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
const val control_widget = "control_widget"

class mi_widget : AppWidgetProvider() {

    fun actualizarWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
        // se apertura archivo de preferencias, para leer los datos almacenados por la actividad
        val datos = context.getSharedPreferences ("DatosWidget", Context.MODE_PRIVATE)
        // se obtiene las variables definidas desde la actividad
        val mensaje = datos.getString("mensaje", "Mensaje Recibio:")

        val controles = RemoteViews ( context. packageName, R.layout.mi_widget)
        controles.setTextViewText(R.id.lblMensaje, mensaje)
        val sdfDate = SimpleDateFormat( "hh:mm a")
        val now = Date()
        val hora = sdfDate.format(now)

        val sdf2 = SimpleDateFormat( "\t\tEE\n\t\tMMM dd")
        val dia = sdf2.format(now)
        controles.setTextViewText(R.id.lblHora, hora)
        controles.setTextViewText(R.id.lblDia, dia)
        val clicenwidget = Intent(context,DatosWidget::class.java)
        val widgetesperando = PendingIntent.getActivity(
            context,widgetId,clicenwidget, PendingIntent.FLAG_CANCEL_CURRENT
        )

        controles.setOnClickPendingIntent(R.id.frmWidget,widgetesperando)
        appWidgetManager.updateAppWidget (widgetId, controles)
    }


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(control_widget==intent?.action){
            val widgetId = intent.getIntExtra("appWidgetId",0)
            actualizarWidget(context!!,AppWidgetManager.getInstance(context),widgetId)
        }
        super.onReceive(context, intent)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.mi_widget)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}