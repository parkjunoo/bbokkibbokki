package com.example.bbokkibbokki.util;

import android.os.AsyncTask;

import com.example.bbokkibbokki.dao.ToDoDao;
import com.example.bbokkibbokki.model.ToDo;

public class InsertAsyncTask extends AsyncTask<ToDo, Void, Void> {
    private ToDoDao mTodoDao;

    public  InsertAsyncTask(ToDoDao todoDao){
        this.mTodoDao = todoDao;
    }

    @Override //백그라운드작업(메인스레드 X)
    protected Void doInBackground(ToDo... todos) {
        //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
        //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
        mTodoDao.insert(todos[0]);
        return null;
    }
}
