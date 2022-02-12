package com.seip.android.covidstat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.seip.android.covidstat.databinding.FragmentCovidBinding;
import com.seip.android.covidstat.viewmodels.CovidViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CovidFragment extends Fragment {
    private FragmentCovidBinding binding;
    private CovidViewModel viewModel;
    public CovidFragment() {
        // Required empty public constructor
    }
//
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.weather_menu, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        searchView.setQueryHint("Search a city");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query!=null){
                    viewModel.setCity(query);
                    viewModel.loadData();
                    searchView.setQuery(null,false);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCovidBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(requireActivity()).get(CovidViewModel.class);
        viewModel.loadData();
        viewModel.getResponseInfoLiveData().observe(getViewLifecycleOwner(), covidResponseModel -> {
            binding.countryNameTV.setText(covidResponseModel.getCountry());
            Picasso.get().load(covidResponseModel.getCountryInfo().getFlag())
                    .fit()
                    .into(binding.countryFlag);
            binding.dateTimeTV.setText(new SimpleDateFormat("MMM dd, yyyy").format(new Date(covidResponseModel.getUpdated())));
            binding.caseToday.setText(String.valueOf(covidResponseModel.getTodayCases()));
            binding.deathToday.setText(String.valueOf(covidResponseModel.getTodayDeaths()));
            binding.recoveredToday.setText(String.valueOf(covidResponseModel.getTodayRecovered()));
            binding.totalCaseTV.setText(String.valueOf(covidResponseModel.getCases()));
            binding.totalDeathTV.setText(String.valueOf(covidResponseModel.getDeaths()));
            binding.totalRecoverTV.setText(String.valueOf(covidResponseModel.getRecovered()));
        });


        return binding.getRoot();
    }
}