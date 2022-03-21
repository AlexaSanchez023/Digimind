package sanchez.alexa.digimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sanchez.alexa.digimind.databinding.FragmentHomeBinding
import sanchez.alexa.digimind.ui.AdaptadorTareas
import sanchez.alexa.digimind.ui.Task

class HomeFragment : Fragment() {

    companion object{
        var tasks: ArrayList<Task> = ArrayList<Task>()
        var first =true
        lateinit var adaptador: AdaptadorTareas
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var gridView: GridView =binding.gridview

       /* if (first){
            fill_Tasks()
            first=false
        }*/
        cargas_Tareas()


        adaptador = AdaptadorTareas(root.context,tasks )

        gridView.adapter= adaptador

        return root
    }

    fun fill_Tasks(){
        tasks.add(Task("tarea 1","lunes","1:30 pm"))
        tasks.add(Task("tarea 2","martes","2:30 pm"))
        tasks.add(Task("tarea 3","miercoles","3:30 pm"))
        tasks.add(Task("tarea 4","jueves","4:30 pm"))
        tasks.add(Task("tarea 5","viernes","5:30 pm"))
    }

    fun cargas_Tareas(){
        val preferencias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()
        var json = preferencias?.getString("tareas",null)

        var type = object : TypeToken<ArrayList<Task?>?>() {}.type

        if (json==null){
            tasks= ArrayList<Task>()

        }else{
            tasks=gson.fromJson(json, type)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}