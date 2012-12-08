@echo /*************************************************************************
@echo * Copyright (c) 2011 cnfree.
@echo * All rights reserved. This program and the accompanying materials
@echo * are made available under the terms of the Eclipse Public License v1.0
@echo * which accompanies this distribution, and is available at
@echo * http://www.eclipse.org/legal/epl-v10.html
@echo *
@echo * Contributors:
@echo * cnfree - initial API and implementation
@echo **************************************************************************/
cd /d %~dp0
start "" ".\jre\bin\javaw.exe" -cp ".\app\bluetoothHandle.jar;" my.sunny.ui.MainClientUI 