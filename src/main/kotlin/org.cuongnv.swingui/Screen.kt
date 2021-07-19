package org.cuongnv.swingui

import kotlin.jvm.JvmOverloads
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.Color
import java.awt.FlowLayout
import java.awt.ComponentOrientation
import javax.swing.JButton
import javax.swing.AbstractAction
import java.awt.event.ActionEvent
import java.awt.BorderLayout
import java.awt.Container
import javax.swing.border.EmptyBorder
import java.awt.Font
import java.awt.Toolkit
import javax.swing.border.LineBorder
import javax.swing.ImageIcon
import javax.swing.SwingConstants
import java.awt.event.MouseMotionListener
import java.awt.event.MouseMotionAdapter
import java.awt.event.MouseListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JLabel
import javax.swing.WindowConstants
import kotlin.system.exitProcess
import org.cuongnv.swingui.material.FlatButtonUI
import org.cuongnv.swingui.res.Values
import org.cuongnv.swingui.utils.ViewUtils

/*
 * View size:        900x600
 * Title size:       900x40
 * Fragment size:    900x480
 * Navibar size:     900x80
 *
 * */
abstract class Screen @JvmOverloads constructor(initWidth: Int = DEFAULT_WIDTH, initHeight: Int = DEFAULT_HEIGHT) {
    companion object {
        protected const val DEFAULT_WIDTH = 900
        protected const val DEFAULT_HEIGHT = 600
        private const val TITLE_BAR_HEIGHT = 40
    }

    private val screenSize = Toolkit.getDefaultToolkit().screenSize
    val window: JFrame = JFrame()
    private lateinit var titlePanel: JPanel
    private lateinit var _contentPanel: JPanel

    private val windowWidth: Int = initWidth
    private val windowHeight: Int = initHeight

    val width: Int get() = windowHeight - TITLE_BAR_HEIGHT
    val height: Int get() = windowHeight - TITLE_BAR_HEIGHT
    val container: Container get() = _contentPanel

    private var movingX = 0
    private var movingY = 0
    private var isMoving = false

    private val draggable: MouseMotionListener = object : MouseMotionAdapter() {
        override fun mouseDragged(e: MouseEvent) {
            if (isMoving) {
                val point = e.locationOnScreen
                window.setLocation(point.x - movingX, point.y - movingY)
            }
        }
    }
    private val movable: MouseListener = object : MouseAdapter() {
        override fun mousePressed(e: MouseEvent) {
            movingX = e.x
            movingY = e.y
            isMoving = true
        }

        override fun mouseReleased(e: MouseEvent) {
            isMoving = false
        }
    }

    init {
        window.apply {
            preferredSize = Dimension(initWidth, initHeight)
            isUndecorated = true
            background = Color.white
            this.setLocation(
                screenSize.width / 2 - windowWidth / 2,
                screenSize.height / 2 - windowHeight / 2
            )
        }

        setupFrame()

        this.onCreateView()
        this.onStart()

        window.pack()
        window.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        this.onViewCreated()
    }

    private fun setupFrame() {
        val buttonClose = createTitleAction(ViewUtils.getImageIcon("icons8-multiply-24.png"))
        buttonClose.addActionListener(object : AbstractAction() {
            override fun actionPerformed(actionEvent: ActionEvent) {
                onClose()
                window.dispose()
                exitProcess(0)
            }
        })

        val titleActionContainer = JPanel().apply {
            layout = FlowLayout(FlowLayout.RIGHT, 0, 0)
            componentOrientation = ComponentOrientation.RIGHT_TO_LEFT
            add(buttonClose)
        }

        val appTitle = JLabel(getAppTitle()).apply {
            border = EmptyBorder(10, 10, 10, 10)
            font = Font(Values.FONT, Font.BOLD, 10)
        }

        titlePanel = JPanel().apply {
            preferredSize = Dimension(windowWidth, TITLE_BAR_HEIGHT)
            background = Color.white
            addMouseListener(movable)
            addMouseMotionListener(draggable)
            layout = BorderLayout()
            add(titleActionContainer, BorderLayout.EAST)
            add(appTitle, BorderLayout.WEST)
        }

        _contentPanel = JPanel().apply {
            preferredSize = Dimension(windowWidth, this@Screen.height)
            background = Color.white
        }

        val rootPanel = JPanel().apply {
            preferredSize = Dimension(windowWidth, windowHeight)
            layout = BorderLayout()
            add(titlePanel, BorderLayout.NORTH)
            add(_contentPanel, BorderLayout.CENTER)
            border = LineBorder(Color.decode("#999999"), 1)
        }
        window.contentPane.add(rootPanel)
    }

    protected open fun getAppTitle(): String = "Hello"

    private fun createTitleAction(imageIcon: ImageIcon): JButton {
        return JButton(imageIcon).apply {
            background = Color.white
            horizontalTextPosition = SwingConstants.CENTER
            isFocusPainted = false
            border = EmptyBorder(30, 30, 30, 30)
            preferredSize = Dimension(
                (TITLE_BAR_HEIGHT * 1.5f).toInt(),
                TITLE_BAR_HEIGHT
            )
            foreground = Color.WHITE
            ui = FlatButtonUI()
        }
    }

    fun visibility(visible: Boolean) {
        window.isVisible = visible
    }

    open fun onStart() {}
    open fun onClose() {}
    abstract fun onCreateView()
    open fun onViewCreated() {}
}