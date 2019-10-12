package com.myproject.app.guice;

import com.myproject.app.controller.ListaSpesaController;
import com.myproject.app.view.AppSwingView;

public interface ListaSpesaControllerFactory {
	ListaSpesaController create(AppSwingView view);
}
