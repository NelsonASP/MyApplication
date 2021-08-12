package com.nasp.myapplication.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nasp.myapplication.R
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.databinding.HomeFragmentBinding
import com.nasp.myapplication.home.adapter.GifAdapter
import com.nasp.myapplication.home.adapter.MenuFragment
import com.nasp.myapplication.home.adapter.MenuFragment.Companion.KEY_GIF
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.nasp.myapplication.data.remote.Result


class HomeFragment : Fragment(), SearchView.OnQueryTextListener,
    GifAdapter.OnItemClickListener {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private val adapter = GifAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        loadGif()
        selectFavorites()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)

        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favourites -> {
                findNavController().navigate(R.id.favouriteFragment)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(context, 2)
            recyclerview.adapter = adapter
            recyclerview.setHasFixedSize(true)
        }
    }

    private fun setupSearchView() {
        with(binding) {
            searchView.setOnQueryTextListener(this@HomeFragment)
            searchView.isFocusable = false
            searchView.isIconified = false
            searchView.clearFocus()
        }
    }

    private fun loadGif() {
        viewModel.gifs.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource) {
                    is Result.Loading -> {
                        with(binding) {
                            progressCircular.visibility = View.VISIBLE
                            recyclerview.visibility = View.GONE
                        }
                    }
                    is Result.Success -> {
                        with(binding) {
                            progressCircular.visibility = View.GONE
                            recyclerview.visibility = View.VISIBLE
                            adapter.submitList(resource.data)
                        }
                    }
                    is Result.Error -> {
                            Log.i("Error", "Result.Error")
                    }
                }
            }
        })
    }

    private fun selectFavorites() {
        viewModel.favouriteGifs.observe(viewLifecycleOwner, {
            it?.let { list ->
                adapter.favourites = list.map { it.id }
            }
        })

    }

    override fun onItemClicked(items: Items, share: Boolean) {
        if (!share)
            viewModel.saveFavorites(items)
        else {
            val menuFragment = MenuFragment()
            val args = Bundle().apply { putParcelable(KEY_GIF, items) }
            menuFragment.arguments = args
            menuFragment.show(childFragmentManager, "tag")
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onTextSearched(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        onTextSearched(newText)
        return true
    }

    private fun onTextSearched(text: String?) {
        if (text != null)
            viewModel.searchGif(text)
    }

}