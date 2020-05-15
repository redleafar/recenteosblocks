package one.block.recenteosblocks.di

import one.block.recenteosblocks.data.db.AppDatabase
import one.block.recenteosblocks.data.network.EosAPI
import one.block.recenteosblocks.data.repositories.BlockRepository
import one.block.recenteosblocks.ui.detail.BlockDetailViewModel
import one.block.recenteosblocks.ui.home.HomeViewModel
import one.block.recenteosblocks.ui.list.ListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel {
        HomeViewModel()
    }
}

val listViewModelModule = module {
    viewModel {
        ListViewModel(get())
    }
}

val blockDetailViewModelModule = module {
    viewModel {
        BlockDetailViewModel(get())
    }
}

val blockRepositoryModule = module {
    single {
        BlockRepository(get(), get())
    }
}

val dbModule = module {
    single {
        AppDatabase(androidApplication())
    }
}

val eosApiModule = module {
    single {
        EosAPI()
    }
}