package com.myproject.app.guice;

import com.myproject.app.controller.ProdottoController;
import com.myproject.app.view.AppSwingView;

public interface ProdottoControllerFactory {
	ProdottoController create(AppSwingView view);
}
