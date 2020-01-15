package com.example.images.view.albumslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.images.R
import com.example.images.entities.Album
import com.example.images.entities.Event
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsListFragment : Fragment() {

    private val viewModel by viewModel<AlbumsListViewModel>()
    private var listener: Listener? = null
    private val disposable = CompositeDisposable()
    private val adapter = AlbumsListAdapter(
        object : AlbumsListAdapter.Listener {
            override fun onItemClicked(albumId: Int) {
                listener?.onAlbumItemClicked(albumId)
            }
        }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        disposable.add(viewModel.output.subscribe { subscribeToViewModel(it) })
        viewModel.fetchAlbums()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initView() {
        albums_recycler_view?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        albums_recycler_view?.adapter = adapter
    }

    private fun subscribeToViewModel(event: Event) {
        when (event) {
            is Event.Progress -> showProgress(event.inProgress)
            is Event.Error -> showError(event.message)
            is Event.Data<*> -> setList(event.data as List<Album>)
        }
    }

    private fun showProgress(show: Boolean) {
        // show progress
    }

    private fun showError(message: String) {
        Snackbar.make(albums_recycler_view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun setList(list: List<Album>) {
        adapter.addToList(list)
    }

    interface Listener {
        fun onAlbumItemClicked(albumId: Int)
    }
}