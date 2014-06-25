import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

// интерфейс, которых определяет необходимые методы
interface TaskList {
	public boolean addNewTaskToDate(String task, Date date);
	public boolean removeTaskFromDate(String task, Date date);
	public String[] getTasksByDate(Date date);
	public String[] getTasksByCurrentDate();
	public Date[] getDates();
}

// сравниваем даты, игнорируя милисекунды
class DateComparator implements Comparator<Date> {
	@Override
	public int compare(Date arg0, Date arg1) {
		long t1 = arg0.getTime(); // первая дата в мс.
		long t2 = arg1.getTime(); // вторая дата в мс.
		if (Math.abs(t1 - t2) < 1000) // если указанные даты отличаются не более, чем на секунду - они идентичны
			return 0;
		else if (t1<t2) return
				-1;
		else
			return 1;
	}
}

// класс, реализующий интерфейс
class TaskListImpl implements TaskList {
	private TreeMap<Date, Set<String>> tasksByDate = new TreeMap(new DateComparator()); // карта отношений дат к названиям дел
	
	public TaskListImpl(){};

	// добавить новое задание в ежедневник
	@Override
	public boolean addNewTaskToDate(String task, Date date) {
		if (tasksByDate.containsKey(date))
			return tasksByDate.get(date).add(task); // если такая дата есть - добавляем дело в список дел данной даты
		else {
			Set<String> newTasks = new TreeSet<String>(); // создаем пустой список
			newTasks.add(task); // добавляем туда название дела
			tasksByDate.put(date, newTasks); // добавляем отношение в карту
			return true;
		}
	}

	// удалить задание из ежедневника
	@Override
	public boolean removeTaskFromDate(String task, Date date) {
		return tasksByDate.get(date).remove(task);
	}

	// узнать названия заданий, привязанных к дате
	@Override
	public String[] getTasksByDate(Date date) {
		if (tasksByDate.containsKey(date) == false) // если указанной даты не в списке 
			return null;
		if (tasksByDate.get(date).size() != 0) { // если к указанной дате привязано хотя бы одно задание
			String tasks[] = new String[tasksByDate.get(date).size()]; // создаем массив названий дел
			tasksByDate.get(date).toArray(tasks); // получаем названия дел, записывая их в массив
			return tasks;
		}
		else
			return null;
	}

        //
	@Override
	public String[] getTasksByCurrentDate() {
		return getTasksByDate(new Date()); // получаем массив названий дел для сегодняшней даты
	}

	@Override
	public Date[] getDates() {
		if (tasksByDate.keySet().size() != 0) { // если в карте есть хотя бы одна дата
			Date dates[] = new Date[tasksByDate.keySet().size()]; // создаем массив дат
			tasksByDate.keySet().toArray(dates); // записываем даты в массив
			return dates;
		}
		return null;
	}
}

public class NewJFrame extends javax.swing.JFrame {

    private TaskList taskList = new TaskListImpl(); // класс, который хранит себе список отношений заданий к датам
    private DefaultListModel model = new DefaultListModel(); // класс, осуществляющий взаимодействие со списком на форме
    
    
    public NewJFrame() {
        initComponents(); // стандартный метод, написанной средой разработки; создает все визуальные компоненты
        
        tasksForCurrentDate.setModel(model); // связывание списка на форме с классом для его управления
    }
    
    private void updateTasksForDateList(Date date) {
        model.clear(); // очистить список отображенных дел
                
        String tasks[] = taskList.getTasksByDate(date); // получить массив названий заданий по текущей дате
		if (tasks != null) { // если массив не пуст
			for (String t: tasks) // в цикле распечатать названия заданий
				model.addElement(t);
		}
                else
                    JOptionPane.showMessageDialog(this, "В списке нет дел для этой даты", "Нет дел", JOptionPane.WARNING_MESSAGE);
    }

    // метод, созданный средой разработки (IDE) Нельзя и не нужно изменять и вовсе трогать :)
     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        newDateEdit = new javax.swing.JTextField();
        newTaskEdit = new javax.swing.JTextField();
        addTaskToDateButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tasksForCurrentDate = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        dateForSearch = new javax.swing.JTextField();
        findTask = new javax.swing.JButton();
        resultLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Дата:");

        jLabel2.setText("Задание:");

        newDateEdit.setText("7.06.2014");

        newTaskEdit.setText("Выполнить лабораторную");
        newTaskEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTaskEditActionPerformed(evt);
            }
        });

        addTaskToDateButton.setText("Добавить");
        addTaskToDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTaskToDateButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(tasksForCurrentDate);

        jLabel3.setText("Список заданий для даты:");

        dateForSearch.setText("25.06.2014");
        dateForSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateForSearchActionPerformed(evt);
            }
        });

        findTask.setText("Найти задание");
        findTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findTaskActionPerformed(evt);
            }
        });

        resultLabel.setText("Статус:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newDateEdit)
                            .addComponent(newTaskEdit)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(dateForSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(findTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addTaskToDateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(resultLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(newDateEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(newTaskEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addTaskToDateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(resultLabel)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dateForSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findTask))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newTaskEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTaskEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newTaskEditActionPerformed

    private void addTaskToDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTaskToDateButtonActionPerformed
        if (newDateEdit.getText().equals("") || newTaskEdit.getText().equals("")) // если пользователь не указал дату или название дела
            JOptionPane.showMessageDialog(this, "Заполните необходимые поля", "Ошибка: не все поля заполнены", JOptionPane.WARNING_MESSAGE);
        else { // если дата указана правильно и было указано название дела
            String newDateString = newDateEdit.getText(); // считать дату из текстового поля
            newDateString = newDateString.replace(".", "/"); // заменить все точки на слеши
			
            String dateValues[] = newDateString.split("/"); // разбить введенную строку на массив строк, разделенных символом "/"
			
            Date newDate; // объявить переменную класса Дата
			
            // распарсить дату, получив день, месяц и год
            try {
		// записать соответствующие составляющие даты в соответствующие целочисленные переменные
		int day = Integer.parseInt(dateValues[0]);
		int month = Integer.parseInt(dateValues[1])-1; // месяцы в этом классе нумеруются с нуля
		int year = Integer.parseInt(dateValues[2])-1900; // дата считается с момента начала 1990 года
				
		newDate = new Date(year, month, day); // создать чистую дату с необходимыми параметрами (но с нулевым временем: 0 часов 0 минут 0 сек)
            }
            catch (Exception e) { newDate = null; }
			
		// если удалось создать новую дату
		if (newDate != null) {
			String newTask = newTaskEdit.getText(); // считать формулировку задания из текстового поля на форме
				
			taskList.addNewTaskToDate(newTask, newDate); // добавить задание к дате
                        
                        resultLabel.setText("Статус: задание '" + newTask + "' добавлено"); // отобразить статус задания
		}
                else
                    JOptionPane.showMessageDialog(this, "Некорректный формат даты", "Ошибка", JOptionPane.WARNING_MESSAGE);
         }
    }//GEN-LAST:event_addTaskToDateButtonActionPerformed

    private void dateForSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateForSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateForSearchActionPerformed

    private void findTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findTaskActionPerformed
        String newDateString = dateForSearch.getText(); // считать дату из поля
        newDateString = newDateString.replace(".", "/"); // заменить все точки на слеши
			
        String dateValues[] = newDateString.split("/"); // разбить введенную строку на массив строк, разделенных символом "/"
	
        Date newDate = null;
        
        // распарсить дату, получив день, месяц и год
        try {
            // записать соответствующие составляющие даты в соответствующие целочисленные переменные переменные
            int day = Integer.parseInt(dateValues[0]);
            int month = Integer.parseInt(dateValues[1])-1;
            int year = Integer.parseInt(dateValues[2])-1900;
				
            newDate = new Date(year, month, day); // создать чистую дату с необходимыми параметрами (но с нулевым временем: 0 часов 0 минут 0 сек)
        }
        catch (Exception e) { newDate = null; }
        
        // если удалось создать объект класса Дата
        if (newDate != null)
            updateTasksForDateList(newDate); // обновить визуальный список на форме, отобразив там дела, привязанные к указанной дате
        else
            JOptionPane.showMessageDialog(this, "Некорректный формат даты", "Ошибка", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_findTaskActionPerformed

    public static void main(String args[]) {
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTaskToDateButton;
    private javax.swing.JTextField dateForSearch;
    private javax.swing.JButton findTask;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField newDateEdit;
    private javax.swing.JTextField newTaskEdit;
    private javax.swing.JLabel resultLabel;
    private javax.swing.JList tasksForCurrentDate;
    // End of variables declaration//GEN-END:variables
}
