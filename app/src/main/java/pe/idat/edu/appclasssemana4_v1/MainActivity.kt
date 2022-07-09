package pe.idat.edu.appclasssemana4_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import pe.idat.edu.appclasssemana4_v1.common.AppMensaje
import pe.idat.edu.appclasssemana4_v1.common.TipoMensaje
import pe.idat.edu.appclasssemana4_v1.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener{

    private lateinit var binding: ActivityMainBinding
    private val listapreferencias = ArrayList<String>()
    private val listausuarios = ArrayList<String>()
    private var estadocivil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_civil_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spestadocivil.adapter = adapter
        }
        binding.spestadocivil.onItemSelectedListener=this
        binding.btnRegistrar.setOnClickListener(this)
        binding.btnListar.setOnClickListener(this)
        binding.cbDeportes.setOnClickListener(this)
        binding.cbDibujo.setOnClickListener(this)
        binding.cbOtros.setOnClickListener(this)
    }

    fun validarGenero():Boolean{
        var respuesta=true
        if (binding.radioGroup.checkedRadioButtonId==-1){
            respuesta=false
        }
        return respuesta
    }

    fun validarNombreApellido(): Boolean{
        var respuesta = true
        if(binding.etNombre.text.toString().trim().isEmpty()){
            binding.etNombre.isFocusableInTouchMode=true
            binding.etNombre.requestFocus()
            respuesta=false
        }else if (binding.etApellido.text.toString().trim().isEmpty()){
            binding.etApellido.isFocusableInTouchMode=true
            binding.etApellido.requestFocus()
            respuesta=false
        }
        return respuesta
    }

    private fun setearControles(){
        listapreferencias.clear()
        binding.etNombre.setText("")
        binding.etApellido.setText("")
        binding.swNotificacion.isChecked=false
        binding.radioGroup.clearCheck()
        binding.cbDeportes.isChecked=false
        binding.cbDibujo.isChecked=false
        binding.cbOtros.isChecked=false
        binding.spestadocivil.setSelection(0)
        binding.etNombre.isFocusableInTouchMode=true
        binding.etNombre.requestFocus()
    }

    fun validarEstadoCivil(): Boolean{
        var respuesta = true
        if(estadocivil == ""){
            respuesta=false
        }
        return respuesta
    }

    fun validarPreferencias():Boolean{
        var respuesta=false
        if(binding.cbDeportes.isChecked||binding.cbDibujo.isChecked||binding.cbOtros.isChecked){
            respuesta=true
        }
        return respuesta
    }

    fun validarFormulario(): Boolean{
        var respuesta = false
        if (!validarNombreApellido()){
            AppMensaje.enviarMensaje(binding.root, "Ingrese su nombre y apellido", TipoMensaje.ERROR)
        }else if(!validarGenero()){
            AppMensaje.enviarMensaje(binding.root, "Seleccione su genero", TipoMensaje.ERROR)
        }else if(!validarPreferencias()){
            AppMensaje.enviarMensaje(binding.root, "Seleccione al menos una preferencia", TipoMensaje.ERROR)
        }else if(!validarEstadoCivil()){
            AppMensaje.enviarMensaje(binding.root, "Seleccione su estado civil", TipoMensaje.ERROR)
        }else{
            respuesta=true
        }
        return respuesta
    }

    override fun onClick(view: View) {
        if(view is CheckBox){
            agregarQuitarPreferencias(view)
        }else{
            when(view.id){
                R.id.btnListar -> irListaPersonas()
                R.id.btnRegistrar -> registrarPersonas()
            }
        }

    }

    private fun registrarPersonas() {
        if (validarFormulario()){
            val infoPersona=binding.etNombre.text.toString()+"  "+
                    binding.etApellido.text.toString()+"  "+
                    obtenerGeneroSeleccionado()+"  "+
                    obtenerPreferenciasSelecciondas()+"  "+
                    estadocivil +"  "+
                    binding.swNotificacion.isChecked
            listausuarios.add(infoPersona)
            AppMensaje.enviarMensaje(binding.root,
                "Persona registrada correctamente", TipoMensaje.SUCCESSFULL)
            setearControles()
        }
    }

    //Funcion para la informacion al otro activity
    private fun irListaPersonas() {
        val intentLista = Intent(this, ListaActivity::class.java)
            .apply {
                putExtra("listpersonas", listausuarios)
            }
        startActivity(intentLista)
    }

    fun obtenerGeneroSeleccionado():String{
        var genero = ""
        when(binding.radioGroup.checkedRadioButtonId){
         R.id.rbtMasculino -> genero=binding.rbtMasculino.text.toString()
         R.id.rbtFemenino -> genero=binding.rbtFemenino.text.toString()
        }
        return genero
    }

    fun obtenerPreferenciasSelecciondas():String{
        var preferencias = ""
        for (pref in listapreferencias){
            preferencias += "$pref - "
        }
        return preferencias
    }

    private fun agregarQuitarPreferencias(view: CheckBox) {
        val cbprefencias = view
        if (cbprefencias.isChecked) {
            when (cbprefencias.id) {
                R.id.cbDeportes -> listapreferencias.add(cbprefencias.text.toString())
                R.id.cbDibujo -> listapreferencias.add(cbprefencias.text.toString())
                R.id.cbOtros -> listapreferencias.add(cbprefencias.text.toString())
            }
        } else {
            when (cbprefencias.id) {
                R.id.cbDeportes -> listapreferencias.remove(cbprefencias.text.toString())
                R.id.cbDibujo -> listapreferencias.remove(cbprefencias.text.toString())
                R.id.cbOtros -> listapreferencias.remove(cbprefencias.text.toString())
            }
        }
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        estadocivil=if (position > 0){
            adapter!!.getItemAtPosition(position).toString()
        }else{
            ""
        }


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}