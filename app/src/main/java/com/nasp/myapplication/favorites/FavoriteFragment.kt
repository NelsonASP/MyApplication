package com.nasp.myapplication.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.nasp.myapplication.R
import com.nasp.myapplication.data.local.Items
import com.nasp.myapplication.databinding.FavoriteFragmentBinding
import com.nasp.myapplication.home.adapter.GifAdapter
import com.nasp.myapplication.home.adapter.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), GifAdapter.OnItemClickListener {

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModel()

    private val adapter = GifAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setHasOptionsMenu(true)
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        loadFavorites()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)

        menuInflater.inflate(R.menu.favorite_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_favourites -> {
                viewModel.deleteAllFavorites()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycler() {
        with(binding) {
            recyclerview.layoutManager = GridLayoutManager(context, 1)
            recyclerview.adapter = adapter
        }
    }

    private fun loadFavorites() {
        viewModel.favouriteGifs.observe(viewLifecycleOwner, {
            it?.let { list ->
                with(binding) {
                    progressCircular.visibility = View.GONE

                    if(list.isNullOrEmpty()) {
                       recyclerview.visibility = View.GONE
                    } else {
                        recyclerview.visibility = View.VISIBLE
                        adapter.favourites = list.map { it.id }
                        adapter.submitList(list)
                    }
                }
            }
        })
    }

    override fun onItemClicked(items: Items, share: Boolean) {
        if (!share)
            viewModel.saveFavorite(items)
        else {
            val menuFragment = MenuFragment()
            val args = Bundle().apply { putParcelable(MenuFragment.KEY_GIF, items) }
            menuFragment.arguments = args
            menuFragment.show(childFragmentManager, "tag")
        }
    }
}