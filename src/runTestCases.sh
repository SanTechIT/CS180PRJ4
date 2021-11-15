chmod +x ../hamcrest.jar
chmod +x ../junit4.jar
javac -cp .:../junit4.jar:../hamcrest.jar Tests*.java

java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore Tests*

# These can be individaully commented and uncommented to test individual classes
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsCourse
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsDiscussion
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsDisplay
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsMain
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsPost
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsStudent
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsStudentRunner
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsTeacher
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsTeacherRunner
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsUser
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsUserRunner
