/* Copyright (c) 2010, Patrick Tierney 
 * All rights reserved.
 * 
 * This file is part of the "Live Top Five" vote visualizer 
 * created for Mr Youth August 2010 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * */

package voteVis;

public enum Size {
	XS,
	S,
	M,
	L,
	XL;
	
	public static int get_dim_from_size(Size size) {
		switch (size) {
		case XL:
			return Settings.UNIT_DIM * 3 + Settings.BOX_GAP * 2;
		case L:
			return Settings.UNIT_DIM * 2 + Settings.BOX_GAP;
		case M:
			return (int) (Settings.UNIT_DIM * 1.7 + Settings.BOX_GAP); // not 1.75 / 1.25 for rounding size errors
		case S:
			return (int) (Settings.UNIT_DIM * 1.2 + Settings.BOX_GAP);
		case XS:
		default:
			return Settings.UNIT_DIM;
		}
	}
	
	// rank 0 = #1 (most votes)
	// rank 1 = #2
	// ...
	public static Size get_size_from_rank(int rank) {
		switch (rank) {
		case 0:
			return XL;
		case 1:
			return L;
		case 2:
			return M;
		case 3:
			return S;
		case 4:
		default:
			return XS;
		}
	}
}
