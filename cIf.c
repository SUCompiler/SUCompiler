{ 
    i = 3;  j = 4;
    
    ;;;i=2;;;
    

    if (i == j) {
    	t = 200;
    }
    else {
    	f = -200;
    }

    if (i < j) { 
    	t = 300; 
    }

    if      (i == 1) { f = 10; }
    else if (i == 2) { f = 20; }
    else if (i == 3) { t = 30; }
    else if (i == 4) { f = 40; }
    else            { f = -1; }

    
    if (i == 3) { 
    	if j == 2 { 
    		t = 500;
    	} else {
		f = -500;
    	}
    }
}