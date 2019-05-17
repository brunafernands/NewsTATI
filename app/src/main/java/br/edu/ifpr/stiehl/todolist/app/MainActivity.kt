package br.edu.ifpr.stiehl.todolist.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifpr.stiehl.todolist.R
import br.edu.ifpr.stiehl.todolist.entidades.NewsResult
import br.edu.ifpr.stiehl.todolist.servicos.ResultsService
import br.edu.ifpr.stiehl.todolist.ui.ResultAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var service: ResultsService
    lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configuraRetrofit()
        carregaDados()
    }

    fun configuraRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ResultsService::class.java)
    }

    fun carregaDados() {
        service.news("br", "5bf5c78e95d84b0387036b52fcaa02f2", "esportes").enqueue(object : Callback<List<NewsResult>> {
            override fun onFailure(call: Call<List<NewsResult>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<List<NewsResult>>,
                response: Response<List<NewsResult>>
            ) {
                val results = response.body()
                if (results != null)
                    configuraRecyclerView(results)
            }
        })
    }

    fun configuraRecyclerView(results: List<NewsResult>) {
        adapter = ResultAdapter(results.toMutableList())
        listTarefas.adapter = adapter

        listTarefas.layoutManager = LinearLayoutManager(
            this, RecyclerView.VERTICAL, false)
    }
}
