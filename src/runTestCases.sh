javac -cp .:../junit4.jar:../hamcrest-2.2.jar Tests*.java

# for each compiled Tests file in the src directory, run the tests
for f in ./Tests*.class; do
    if [[ $f != "./Tests.class" ]]; then
        echo "FILENAME " + $f
        java -cp .:../junit4.jar:../hamcrest-2.2.jar org.junit.runner.JUnitCore $(basename $f .class)
    fi
done

# These can be individaully commented and uncommented to test individual classes
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsCourse
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsDiscussion
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsMain
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsPost
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsStudentRunner
# java -cp .:../junit4.jar:../hamcrest.jar org.junit.runner.JUnitCore TestsTeacherRunner
