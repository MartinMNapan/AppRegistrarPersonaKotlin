package pe.idat.edu.appclasssemana4_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import pe.idat.edu.appclasssemana4_v1.databinding.ActivityListaBinding
import kotlin.collections.ArrayList

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listpersonas = intent.getSerializableExtra("listpersonas")
            as ArrayList<String>
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            listpersonas
        )
        binding.lvpersonas.adapter = adapter
    }
}