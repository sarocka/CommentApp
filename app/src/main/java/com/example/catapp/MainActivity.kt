package com.example.catapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.catapp.adapters.CommentAdapter
import com.example.catapp.adapters.ViewPagerAdapter
import com.example.catapp.model.Comment
import com.example.catapp.repositories.CommentRepository
import com.example.catapp.viewmodels.MainViewModel
import com.example.catapp.viewmodels.ViewModelFactory
import com.google.android.material.navigation.NavigationView


lateinit var viewModel: MainViewModel
private lateinit var commentAdapter: CommentAdapter
private lateinit var toggle: ActionBarDrawerToggle
private lateinit var drawerLayout: DrawerLayout
private lateinit var navigation: NavigationView
private lateinit var viewPager: ViewPager2

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val repository = CommentRepository()
        val viewModelFactory = ViewModelFactory(repository, this)
        commentAdapter = CommentAdapter(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        initiateToggle()
        navigation = findViewById(R.id.navigation)

        navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item1 -> redirectToLogin()
                R.id.menu_item2 -> redirectToLogin()
            }
            true
        }

        viewModel.apiDataToDb()
        setUpRecyclerView()
        initObservers()
        setUpViewPager()

    }

    fun redirectToLogin() {
        val intent = Intent(this@MainActivity, LogInActivity::class.java)
        startActivity(intent)
    }

    fun setUpViewPager() {
        viewPager = findViewById(R.id.viewPager)
        val fragments = mutableListOf(FragmentFirst(), FragmentSecond())
        val viewPagerAdapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = viewPagerAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId

        if (id == R.id.sort) {
            Log.d("MainActivity", "menu sort")
        } else if (id == R.id.sortAlphabetically) {
            sortAlphabetically()
            Log.d("MainActivity", "sort alphabetically")
        }

        if (toggle.onOptionsItemSelected(item)) {
            return true

        }
        return super.onOptionsItemSelected(item)
    }

    fun initObservers() {
        viewModel.comments.observe({ lifecycle }) {
            it?.let {
                commentAdapter.setData(it as MutableList<Comment>)
            }
        }
    }


    fun setUpRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = commentAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.layoutManager= GridLayoutManager(this,2)
    }


    fun initiateToggle() {
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun deleteComment(position: Int) {
        Log.d("MainActivity", "This is position: ${position}")
        viewModel.deleteComment(position)
        val currentList = commentAdapter.comments.toMutableList()
        currentList.removeAt(position - 1)
        commentAdapter.setData(currentList)
    }

    fun displayToast() {
        Toast.makeText(
            this, "Successfully deleted!",
            Toast.LENGTH_LONG
        ).show();
    }

    fun editComment(comment: Comment, id: Int) {
        viewModel.editComment(comment, id)
        val currentList = commentAdapter.comments.toMutableList()
        currentList.set(id - 1, comment)
        commentAdapter.setData(currentList)
    }

    fun editCommentInDB(comment: Comment, index: Int) {
        Log.d("MainActivity", "Edited comment: ${comment}")
        viewModel.updateCommentFromDB(comment)
        val currentList = commentAdapter.comments.toMutableList()
        currentList.set(index, comment)
        commentAdapter.setData(currentList)
    }

    fun currentComment(comment: Comment) {
        viewModel.updateFragment(comment)
    }

    fun deleteCommentFromDb(comment: Comment) {
        viewModel.deleteCommentFromDb(comment)
        val currentList = commentAdapter.comments.toMutableList()
        currentList.remove(comment)
        commentAdapter.setData(currentList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.header_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }


    private fun sortAlphabetically() {

        viewModel.comments.observe(this) {
            val sortedList = it.sortedBy { comment -> comment.body.lowercase() }
            commentAdapter.setData(sortedList as MutableList<Comment>)
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDB(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDB(query)
        }
        return true
    }

    private fun searchDB(query: String) {
        // val searchQuery="%$query%"
        viewModel.searchDatabase(query)

        viewModel.searchDatabase(query).observe({ lifecycle }) {
            it?.let {
                Log.d("MainActivity", "Size of the filtered list: ${it.size}. Query: ${query}")
                commentAdapter.setData(it as MutableList<Comment>)

            }
        }


    }

    fun displayMsg() {
        viewModel.message.observe({ lifecycle }) {
            it?.let {
                Toast.makeText(this, "${it}", Toast.LENGTH_LONG).show()
            }
        }
    }


}