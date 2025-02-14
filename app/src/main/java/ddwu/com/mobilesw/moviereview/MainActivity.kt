package ddwu.com.mobilesw.moviereview
// 과제명: 영화 리뷰 앱
// 분반: 01 분반
// 학번: 20220789 성명: 이세민
// 제출일: 2024년 6월 22일

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobilesw.moviereview.data.MovieDao
import ddwu.com.mobilesw.moviereview.data.MovieDto
import ddwu.com.mobilesw.moviereview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val REQ_ADD = 100
    val REQ_UPDATE = 200

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var movies : ArrayList<MovieDto>

    val movieDao by lazy {
        MovieDao(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvMovies.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        movies = movieDao.getAllMovies()
        var adapter = MovieAdapter(movies)
        binding.rvMovies.adapter = adapter

        // 영화 삭제 다이얼로그
        adapter.setOnItemLongClickListener(object : MovieAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("삭제 확인")
                    .setMessage("영화 '${movies[position].title}' 을(를) 삭제하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("삭제") { dialogInterface, i ->
                        val deletedTitle = movies[position].title
                        if (movieDao.deleteMovie(movies[position]) > 0) {
                            movies.removeAt(position)
                            binding.rvMovies.adapter?.notifyItemRemoved(position)
                            Toast.makeText(this@MainActivity, "$deletedTitle 삭제됨", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .setNegativeButton("취소", null)
                    builder.show()

                return true
            }
        })

        adapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("dto", movies.get(position) )
                startActivityForResult(intent, REQ_UPDATE)
            }
        })
    }

    // 옵션 메뉴와 검색 기능
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.searchBtn)
        val searchView = searchItem?.actionView as SearchView

        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    val filteredList = movieDao.searchMovies(newText)
                    (binding.rvMovies.adapter as MovieAdapter).apply {
                        movies.clear()
                        movies.addAll(filteredList)
                        notifyDataSetChanged()
                    }
                } else {
                    movies.clear()
                    movies.addAll(movieDao.getAllMovies())
                    binding.rvMovies.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    // 옵션 선택에 따라
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = AlertDialog.Builder(this@MainActivity)
        when(item.itemId) {
            R.id.addBtn -> {
                val addIntent = Intent(this, AddActivity::class.java)
                startActivityForResult(addIntent, REQ_ADD)
            }
            R.id.introduceBtn -> {
                val introduceIntent = Intent(this, IntroduceActivity::class.java)
                startActivity(introduceIntent)
            }
            R.id.finishBtn -> {
                builder.setTitle("앱 종료")
                    .setMessage("앱을 종료하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("종료") { dialogInterface, i -> finish() }
                    .setNegativeButton("취소",null)
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
    }

    // 추가와 수정 반영
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_ADD -> {
                if (resultCode == RESULT_OK) {
                    movies.clear()
                    movies.addAll(movieDao.getAllMovies())
                    binding.rvMovies.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()
                }
            }
            REQ_UPDATE -> {
                if (resultCode == RESULT_OK) {
                    movies.clear()
                    movies.addAll(movieDao.getAllMovies())
                    binding.rvMovies.adapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}