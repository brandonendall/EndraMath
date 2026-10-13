package com.endramath.app

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.WindowInsets
import android.widget.*
import java.util.UUID

/** Native views; each screen is rebuilt from persisted state, independent of learner age/grade. */
class MainActivity : Activity() {
    private lateinit var repository: LearnerRepository
    private lateinit var body: LinearLayout
    private var page = "home"
    private var academyId = "early-number"
    private val ink = Color.rgb(255, 242, 207)
    private val cream = Color.rgb(255, 247, 225)
    private val bronze = Color.rgb(126, 60, 17)
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = LearnerRepository(this)
        page = savedInstanceState?.getString("page") ?: "home"
        academyId = savedInstanceState?.getString("academy") ?: academyId
        render()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("page", page); outState.putString("academy", academyId)
        super.onSaveInstanceState(outState)
    }
    private fun navigate(destination: String) { page = destination; render() }
    @Deprecated("Native back navigation")
    override fun onBackPressed() { if (page == "home") super.onBackPressed() else navigate(if (page == "academy") "academies" else "home") }
    private fun panel(): GradientDrawable = GradientDrawable().apply {
        colors = intArrayOf(Color.argb(238, 44, 21, 11), Color.argb(230, 70, 32, 12)); orientation = GradientDrawable.Orientation.TOP_BOTTOM; cornerRadius = dp(8).toFloat(); setStroke(dp(2), Color.rgb(228, 169, 66))
    }
    private fun render() {
        val scene = android.widget.FrameLayout(this)
        scene.addView(AcademyBackdrop(this), android.widget.FrameLayout.LayoutParams(-1, -1))
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.argb(60, 20, 8, 0), Color.argb(145, 25, 10, 2)))
        }
        root.setOnApplyWindowInsetsListener { view, insets ->
            view.setPadding(insets.systemWindowInsetLeft, insets.systemWindowInsetTop, insets.systemWindowInsetRight, insets.systemWindowInsetBottom); insets
        }
        val header = LinearLayout(this).apply { gravity = Gravity.CENTER_VERTICAL; setPadding(dp(16), dp(4), dp(16), dp(4)) }
        header.addView(TextView(this).apply { setText(R.string.app_name); textSize = 23f; gravity = Gravity.CENTER_VERTICAL; setTextColor(Color.rgb(255, 211, 116)); typeface = Typeface.create("serif", Typeface.BOLD) }, LinearLayout.LayoutParams(0, dp(52), 1f))
        if (page != "home") header.addView(Button(this).apply { setText(R.string.home); isAllCaps = false; setTextColor(cream); background = panel(); minHeight = dp(48); setPadding(dp(14), 0, dp(14), 0); setOnClickListener { navigate("home") } })
        root.addView(header)
        val scroll = ScrollView(this).apply { isFillViewport = true; clipToPadding = false }
        val container = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_HORIZONTAL; setPadding(dp(16), dp(4), dp(16), dp(16)) }
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(16), dp(12), dp(16), dp(12)); background = panel() }
        container.addView(body, LinearLayout.LayoutParams(if (resources.configuration.screenWidthDp > 680) dp(620) else -1, -2))
        scroll.addView(container); root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f)); scene.addView(root, android.widget.FrameLayout.LayoutParams(-1, -1)); setContentView(scene)
        when(page) { "profiles" -> profiles(); "settings" -> settings(); "info" -> information(); "academies" -> academies(); "academy" -> academy(); else -> home() }
    }
    private fun text(value: String, heading: Boolean = false): TextView = TextView(this).apply {
        text = value; setTextColor(ink); textSize = if (heading) 23f else if(repository.largeText) 19f else 16f
        if (heading) { typeface = Typeface.create("serif", Typeface.BOLD); setTextColor(Color.rgb(255, 211, 116)) }
        setPadding(0, dp(4), 0, dp(8)); body.addView(this)
    }
    private fun button(label: String, action: () -> Unit): Button = Button(this).apply {
        text = label; isAllCaps = false; textSize = if(repository.largeText) 18f else 16f
        setTextColor(cream); background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.rgb(120, 51, 9), Color.rgb(46, 20, 9))).apply { cornerRadius = dp(6).toFloat(); setStroke(dp(2), Color.rgb(255, 183, 51)) }
        minHeight = dp(48); setPadding(dp(12), dp(8), dp(12), dp(8))
        body.addView(this, LinearLayout.LayoutParams(-1, -2).apply { bottomMargin = dp(8) })
        setOnClickListener { action() }
    }
    private fun home() {
        val compact = resources.configuration.screenHeightDp < 750
        body.background = null
        body.addView(ImageView(this).apply {
            setImageResource(R.drawable.endramath_logo); scaleType = ImageView.ScaleType.FIT_CENTER
            contentDescription = "Approved EndraMath orange-and-gold logo"
        }, LinearLayout.LayoutParams(-1, dp(if(compact) 80 else 120)))
        val learner = repository.selected()
        button(if(learner == null) "Choose or edit learners" else "Welcome, ${learner.name}! • Choose or edit learners") { navigate("profiles") }
        button(if(learner == null) "Create a learner to begin" else "Continue learning • ${learner.name}") {
            if(repository.selected() == null) editLearner(null) else navigate("academies")
        }
        val grid = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        body.addView(grid)
        listOf(listOf("Practice" to "In development", "Lessons" to "In development"), listOf("Quests" to "Coming later", "Challenges" to "Coming later")).forEach { rowItems ->
            val row = LinearLayout(this)
            grid.addView(row, LinearLayout.LayoutParams(-1, -2))
            rowItems.forEach { (label, detail) ->
                val tile = Button(this).apply {
                    text = getString(R.string.academy_card, label, detail); isAllCaps = false; textSize = if(repository.largeText) 18f else 16f
                    setTextColor(cream); typeface = Typeface.create("serif", Typeface.BOLD)
                    background = panel(); minHeight = dp(if(compact) 60 else 76)
                    setPadding(dp(8), dp(8), dp(8), dp(8))
                    contentDescription = if(label == "Practice" || label == "Lessons") "$label: explore academies; lessons are being prepared" else "$label: coming in a later checkpoint"
                    setOnClickListener {
                        if(label == "Practice" || label == "Lessons") { if(repository.selected() == null) editLearner(null) else navigate("academies") }
                        else AlertDialog.Builder(this@MainActivity).setTitle(label).setMessage("$label are coming in a later checkpoint. Your current profiles and academy navigation are available now.").setPositiveButton("OK", null).show()
                    }
                }
                row.addView(tile, LinearLayout.LayoutParams(0, -2, 1f).apply { setMargins(dp(3), dp(3), dp(3), dp(6)) })
            }
        }
        val utilities = LinearLayout(this)
        body.addView(utilities)
        listOf("Settings" to "settings", "How EndraMath Works" to "info").forEach { (label, route) ->
            utilities.addView(Button(this).apply {
                text = label; isAllCaps = false; textSize = if(repository.largeText) 18f else 16f
                setTextColor(cream); background = panel(); minHeight = dp(48)
                setPadding(dp(8), dp(8), dp(8), dp(8)); setOnClickListener { navigate(route) }
            }, LinearLayout.LayoutParams(0, -2, 1f).apply { setMargins(dp(3), dp(3), dp(3), dp(3)) })
        }
    }
    private fun profiles() {
        text("Your learners", true); text("Each learner has a separate profile and optional companion.")
        button("Add learner") { editLearner(null) }
        repository.learners().forEach { learner ->
            text(learner.name, true)
            button(if(repository.selected()?.id == learner.id) "Selected • ${learner.name}" else "Select ${learner.name}") {
                if(repository.select(learner.id)) navigate("home") else message("Unable to save selection. Please try again.")
            }
            button("Edit ${learner.name}") { editLearner(learner) }
        }
        if(repository.storageError) text("Saved profiles could not be read. They have been preserved; please report this before adding profiles.")
    }
    private fun editLearner(learner: Learner?) {
        val form = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(dp(24), dp(8), dp(24), dp(8)) }
        fun label(value: String) { form.addView(TextView(this).apply { text = value; setTextColor(Color.rgb(53, 30, 18)); textSize = 16f; setPadding(0, dp(8), 0, dp(4)) }) }
        label("Learner name")
        val name = EditText(this).apply { setText(learner?.name ?: ""); hint = "Name"; inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS; setSingleLine(); filters = arrayOf(android.text.InputFilter.LengthFilter(40)) }; form.addView(name)
        label("Age • private placement input")
        val age = EditText(this).apply { setText(learner?.age?.toString() ?: ""); hint = "Age"; inputType = android.text.InputType.TYPE_CLASS_NUMBER; setSingleLine() }; form.addView(age)
        label("Current school grade • private placement input")
        val grade = Spinner(this).apply { adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, LearnerValidation.grades); setSelection(LearnerValidation.grades.indexOf(learner?.grade ?: "Not specified")) }; form.addView(grade)
        label("These inputs will help choose initial questions in a later checkpoint. They never label ability.")
        val scroller = ScrollView(this).apply { addView(form) }
        val dialog = AlertDialog.Builder(this).setTitle(if(learner == null) "Create learner" else "Edit learner").setView(scroller).setNegativeButton("Cancel", null).setPositiveButton("Save", null).create()
        dialog.setOnShowListener { dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val enteredAge = age.text.toString().toIntOrNull()
            val error = LearnerValidation.error(name.text.toString(), enteredAge, grade.selectedItem.toString())
            if(error != null) { message(error); return@setOnClickListener }
            if(repository.save(Learner(learner?.id ?: UUID.randomUUID().toString(), name.text.toString().trim(), enteredAge!!, grade.selectedItem.toString(), learner?.companion ?: Companion.NONE))) {
                dialog.dismiss(); render()
            } else message("Unable to save. Existing profiles have been preserved.")
        } }
        dialog.show()
    }
    private fun settings() {
        text("Settings", true)
        val large = Switch(this).apply { setText(R.string.larger_text); textSize = 18f; setTextColor(ink); minHeight = dp(48); isChecked = repository.largeText }
        body.addView(large)
        large.setOnCheckedChangeListener { _, checked -> try { repository.largeText = checked; render() } catch(_: Exception) { message("Unable to save preference. Please try again.") } }
        text("Dragon companions", true)
        val learner = repository.selected()
        if(learner == null) { text("Create a learner to choose a companion."); button("Create learner") { editLearner(null) } }
        else {
            text("For ${learner.name} • current choice: ${learner.companion.label}")
            text("Dragons are optional and free. All mathematics remains available with No dragon selected.")
            Companion.entries.forEach { companion -> button(companion.label) {
                if(repository.save(learner.copy(companion = companion))) render() else message("Unable to save companion choice.")
            } }
        }
        text("Solaris • friendly orange fire dragon.\nAurora • pink-and-cream pearlescent dragon.\nTemporary text presentation: individual approved dragon artwork is pending.")
        text("Stored on this device. No account or internet connection is needed. Uninstalling removes local data.")
    }
    private fun information() {
        text("How EndraMath Works", true)
        text("Begin wherever you feel comfortable. Foundational mathematics belongs to learners of every age.")
        text("Future learning path: lessons → guided practice → independent practice → quiz → review. There are no speed penalties, punitive timers, or lost achievements.")
        text("“Where Should I Start?” is planned for a later checkpoint. It will suggest topics and skills, never grade-level labels.")
        text("Build 1 • Checkpoint A", true)
        text("This build provides saved learner profiles, preferences, optional companion selection, and academy navigation. Lessons, assessment, and mastery tracking are not implemented yet.")
        text("The exact approved logo is included. The homepage theme now uses your supplied academy reference, adapted for live controls. Lessons, quests, challenges, and rewards remain future content.")
        text("Created and directed by Kat. All core mathematics will remain available without paying for dragon features.")
    }
    private fun academies() {
        if(repository.selected() == null) { navigate("profiles"); return }
        text("Your mathematics academies", true)
        text("Welcome, ${repository.selected()!!.name}. Explore your next steps at your own pace.")
        LearningCatalog.academies.filter { it.inDevelopment }.forEach { item -> button(item.title) { academyId = item.id; navigate("academy") } }
        text("The first two academy spaces are open for development. Lessons are coming in later checkpoints.")
        text("Future academies", true)
        text(LearningCatalog.academies.filterNot { it.inDevelopment }.joinToString("\n") { "${it.title} • coming later" })
    }
    private fun academy() {
        val item = LearningCatalog.academies.first { it.id == academyId }
        text(item.title, true); text(item.summary)
        text("Your academy is being prepared. Lessons, guided practice, independent practice, quizzes, and mastery will arrive in later checkpoints.")
        text("You are welcome here at any age. Learn without time pressure, and choose a dragon only if you want one.")
        button("Explore other academies") { navigate("academies") }
    }
    private fun message(value: String) { Toast.makeText(this, value, Toast.LENGTH_LONG).show() }
}
