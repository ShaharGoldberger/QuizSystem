package model;

public  class passData {

	static ExamRepository er ;
	
	public static ExamRepository getExamRepos() {
        return er;
    }

    public static  void setExamrepos(ExamRepository er1) {
        er = er1;
    }

}
